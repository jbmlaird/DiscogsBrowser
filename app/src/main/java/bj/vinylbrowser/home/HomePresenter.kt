package bj.vinylbrowser.home

import android.content.Context
import android.support.v7.widget.Toolbar
import bj.vinylbrowser.R
import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.model.listing.Listing
import bj.vinylbrowser.model.order.Order
import bj.vinylbrowser.model.search.RootSearchResponse
import bj.vinylbrowser.model.search.SearchResult
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.NavigationDrawerBuilder
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider
import bj.vinylbrowser.wrappers.LogWrapper
import io.reactivex.Single
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Josh Laird on 29/05/2017.
 */
open class HomePresenter constructor(
        val context: Context, val mView: HomeContract.View, val discogsInteractor: DiscogsInteractor,
        val mySchedulerProvider: MySchedulerProvider, val builder: NavigationDrawerBuilder, val controller: HomeEpxController,
        val sharedPrefsManager: SharedPrefsManager, val log: LogWrapper, val daoManager: DaoManager,
        val tracker: AnalyticsTracker
) : HomeContract.Presenter {

    val TAG = javaClass.simpleName!!

    /**
     * Fetches the user information from Discogs then builds the Navigation Drawer.
     *
     * @param toolbar Activity toolbar.
     */
    override fun connectAndBuildNavigationDrawer(toolbar: Toolbar?) {
        mView.showLoading(true)
        fetchMainPageInformation()
                .subscribe({ listing ->
                    controller.setSelling(listing)
                    // As RecyclerView gets detached, these must be called after attaching NavDrawer
                    mView.setDrawer(builder.buildNavigationDrawer(mView.activity as MainActivity?, (mView.activity as MainActivity).router, toolbar))
                },
                        { error ->
                            if (error.cause != null && error.cause!!.cause != null && error.cause!!.cause?.message == "HTTP 403 FORBIDDEN")
                                controller.setConfirmEmail(true)
                            else
                                controller.setOrdersError(true)
                            mView.setDrawer(builder.buildNavigationDrawer(mView.activity as MainActivity?, (mView.activity as MainActivity).router, toolbar))
                            error.printStackTrace()
                            log.e(TAG, "Wtf")
                        })
    }

    override fun retry() {
        fetchMainPageInformation()
                .subscribe({ listing -> controller.setSelling(listing) }
                ) { error ->
                    error.printStackTrace()
                    controller.setOrdersError(true)
                }
    }

    /**
     * Fetches the viewed releases from the database and populates the {@link FirstActivity}.
     */
    override fun buildViewedReleases() {
        val viewedReleases = daoManager.viewedReleases
        controller.setViewedReleases(viewedReleases)
    }

    /**
     * Fetches the user's orders from Discogs.
     *
     * @return The user's orders.
     */
    override fun fetchOrders(): Single<List<Order>> {
        return discogsInteractor.fetchOrders()
                .observeOn(mySchedulerProvider.ui())
                .subscribeOn(mySchedulerProvider.io())
                .doOnSubscribe { controller.setLoadingMorePurchases(true) }
    }

    /**
     * Fetches the user's selling from Discogs.
     *
     * @return The user's selling.
     */
    override fun fetchSelling(): Single<List<Listing>> {
        return discogsInteractor.fetchSelling(sharedPrefsManager.username)
                .observeOn(mySchedulerProvider.ui())
                .subscribeOn(mySchedulerProvider.io())
    }

    override fun showLoadingRecommendations(isLoading: Boolean) {
        controller.setLoadingRecommendations(isLoading)
    }

    /**
     * Builds recommendations for the user by contacting Discogs with a request requesting tracks
     * in the same genre and label as the last release they looked at. It then populates
     * the {@link FirstActivity}.
     */
    override fun buildRecommendations() {
        val viewedReleases = daoManager.viewedReleases
        if (viewedReleases.size > 0) {
            val latestReleaseViewedStyle = viewedReleases[0].style
            val latestReleaseViewedLabel = viewedReleases[0].labelName
            discogsInteractor.searchByStyle(latestReleaseViewedStyle, "1", false) // Get results for those genres
                    .subscribeOn(mySchedulerProvider.io())
                    .flatMap { (pagination) ->
                        // Get a random page from the search results
                        var maxPageNumber = pagination.pages
                        // This was causing an internal server error if the page number was too high
                        if (maxPageNumber > 100) maxPageNumber = 100
                        val randomPageNumber = ThreadLocalRandom.current().nextInt(1, maxPageNumber + 1)
                        discogsInteractor.searchByStyle(latestReleaseViewedStyle, randomPageNumber.toString(), true)
                    }
                    .map(RootSearchResponse::searchResults)
                    .flattenAsObservable<SearchResult> { searchResults -> searchResults }
                    .take(12)
                    .distinct()
                    // Merge the list with releases from the label that their last viewed release was on
                    .concatWith(discogsInteractor.searchByLabel(latestReleaseViewedLabel)
                            .observeOn(mySchedulerProvider.io())
                            .flattenAsObservable<SearchResult> { searchResults -> searchResults }
                            .take(12)
                            .distinct())
                    .toList()
                    .map<List<SearchResult>> { searchResults ->
                        searchResults.shuffle()
                        searchResults
                    }
                    .observeOn(mySchedulerProvider.ui())
                    .subscribe({ recommendationList -> controller.setRecommendations(recommendationList) }) { error ->
                        error.printStackTrace()
                        controller.setRecommendationsError(true)
                    }
        } else {
            controller.setRecommendations(emptyList<SearchResult>())
        }
    }

    /**
     * Chained requests to fetch the user details from Discogs.
     */
    fun fetchMainPageInformation(): Single<List<Listing>> {
        return discogsInteractor.fetchUserDetails()
                .observeOn(mySchedulerProvider.ui())
                .flatMap { userDetails ->
                    tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.logged_in), userDetails.username, "1")
                    sharedPrefsManager.storeUserDetails(userDetails)
                    fetchOrders()
                }
                .flatMap { orders ->
                    controller.setOrders(orders)
                    fetchSelling()
                }
                .flattenAsObservable { listings -> listings }
                .filter({ (status) -> status == ("For Sale") })
                .toList()
    }
}