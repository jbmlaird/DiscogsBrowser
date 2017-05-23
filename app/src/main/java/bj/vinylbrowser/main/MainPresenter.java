package bj.vinylbrowser.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import bj.vinylbrowser.R;
import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.greendao.ViewedRelease;
import bj.vinylbrowser.model.listing.Listing;
import bj.vinylbrowser.model.order.Order;
import bj.vinylbrowser.model.search.RootSearchResponse;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.NavigationDrawerBuilder;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.LogWrapper;
import io.reactivex.Single;

/**
 * Created by Josh Laird on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private MainContract.View mView;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private NavigationDrawerBuilder navigationDrawerBuilder;
    private MainController mainController;
    private SharedPrefsManager sharedPrefsManager;
    private LogWrapper log;
    private DaoManager daoManager;
    private AnalyticsTracker tracker;

    public MainPresenter(@NonNull Context context, @NonNull MainContract.View view, @NonNull DiscogsInteractor discogsInteractor,
                         @NonNull MySchedulerProvider mySchedulerProvider, @NonNull NavigationDrawerBuilder navigationDrawerBuilder,
                         @NonNull MainController mainController, @NonNull SharedPrefsManager sharedPrefsManager,
                         @NonNull LogWrapper log, @NonNull DaoManager daoManager, @NonNull AnalyticsTracker tracker)
    {
        this.context = context;
        mView = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.navigationDrawerBuilder = navigationDrawerBuilder;
        this.mainController = mainController;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
        this.daoManager = daoManager;
        this.tracker = tracker;
    }

    /**
     * Fetches the user information from Discogs then builds the Navigation Drawer.
     *
     * @param toolbar Activity toolbar.
     */
    @Override
    public void connectAndBuildNavigationDrawer(Toolbar toolbar)
    {
        mView.showLoading(true);
        fetchMainPageInformation()
                .subscribe(listing ->
                        {
                            mainController.setSelling(listing);
                            // As RecyclerView gets detached, these must be called after attaching NavDrawer
                            mView.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(mView.getActivity(), toolbar));
                            mView.setupRecyclerView();
                        },
                        error ->
                        {
                            if (error.getCause() != null && error.getCause().getCause() != null && error.getCause().getCause().getMessage().equals("HTTP 403 FORBIDDEN"))
                                mainController.setConfirmEmail(true);
                            else
                                mainController.setOrdersError(true);
                            mView.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(mView.getActivity(), toolbar));
                            mView.setupRecyclerView();
                            error.printStackTrace();
                            log.e(TAG, "Wtf");
                        });
    }

    /**
     * Fetches the viewed releases from the database and populates the {@link MainActivity}.
     */
    @Override
    public void buildViewedReleases()
    {
        List<ViewedRelease> viewedReleases = daoManager.getViewedReleases();
        mainController.setViewedReleases(viewedReleases);
    }

    /**
     * Chained requests to fetch the user details from Discogs.
     */
    private Single<List<Listing>> fetchMainPageInformation()
    {
        return discogsInteractor.fetchUserDetails()
                .observeOn(mySchedulerProvider.ui())
                .flatMap(userDetails ->
                {
                    tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.logged_in), userDetails.getUsername(), "1");
                    sharedPrefsManager.storeUserDetails(userDetails);
                    return fetchOrders();
                })
                .flatMap(orders ->
                {
                    mainController.setOrders(orders);
                    return fetchSelling();
                })
                .flattenAsObservable(listings -> listings)
                .filter(listing -> listing.getStatus().equals("For Sale"))
                .toList();
    }

    @Override
    public void retry()
    {
        fetchMainPageInformation()
                .subscribe(listing ->
                                mainController.setSelling(listing),
                        error ->
                        {
                            error.printStackTrace();
                            mainController.setOrdersError(true);
                        });
    }

    /**
     * Fetches the user's orders from Discogs.
     *
     * @return The user's orders.
     */
    @Override
    public Single<List<Order>> fetchOrders()
    {
        return discogsInteractor.fetchOrders()
                .observeOn(mySchedulerProvider.ui())
                .subscribeOn(mySchedulerProvider.io())
                .doOnSubscribe(disposable -> mainController.setLoadingMorePurchases(true));
    }

    /**
     * Fetches the user's selling from Discogs.
     *
     * @return The user's selling.
     */
    @Override
    public Single<List<Listing>> fetchSelling()
    {
        return discogsInteractor.fetchSelling(sharedPrefsManager.getUsername())
                .observeOn(mySchedulerProvider.ui())
                .subscribeOn(mySchedulerProvider.io());
    }

    /**
     * Builds recommendations for the user by contacting Discogs with a request requesting tracks
     * in the same genre and label as the last release they looked at. It then populates
     * the {@link MainActivity}.
     */
    @Override
    public void buildRecommendations()
    {
        List<ViewedRelease> viewedReleases = daoManager.getViewedReleases();
        if (viewedReleases.size() > 0)
        {
            String latestReleaseViewedStyle = viewedReleases.get(0).getStyle();
            String latestReleaseViewedLabel = viewedReleases.get(0).getLabelName();
            discogsInteractor.searchByStyle(latestReleaseViewedStyle, "1", false) // Get results for those genres
                    .subscribeOn(mySchedulerProvider.io())
                    .flatMap(rootSearchResponse ->
                    {
                        // Get a random page from the search results
                        int maxPageNumber = rootSearchResponse.getPagination().getPages();
                        int randomPageNumber = ThreadLocalRandom.current().nextInt(1, maxPageNumber + 1);
                        return discogsInteractor.searchByStyle(latestReleaseViewedStyle, String.valueOf(randomPageNumber), true);
                    })
                    .map(RootSearchResponse::getSearchResults)
                    .flattenAsObservable(searchResults ->
                            searchResults)
                    .take(12)
                    // Merge the list with releases from the label that their last viewed release was on
                    .concatWith(discogsInteractor.searchByLabel(latestReleaseViewedLabel)
                            .observeOn(mySchedulerProvider.io())
                            .flattenAsObservable(searchResults ->
                                    searchResults)
                            .take(12))
                    .toList()
                    .map(searchResults ->
                    {
                        Collections.shuffle(searchResults);
                        return searchResults;
                    })
                    .observeOn(mySchedulerProvider.ui())
                    .subscribe(recommendationList ->
                            mainController.setRecommendations(recommendationList), error ->
                    {
                        error.printStackTrace();
                        mainController.setRecommendationsError(true);
                    });
        }
        else
        {
            mainController.setRecommendations(Collections.emptyList());
        }
    }

    @Override
    public void showLoadingRecommendations(boolean isLoading)
    {
        mainController.setLoadingRecommendations(isLoading);
    }
}