package bj.vinylbrowser.home

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.customviews.MyRecyclerView
import bj.vinylbrowser.marketplace.MarketplaceController
import bj.vinylbrowser.order.OrderController
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.singlelist.SingleListController
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.afollestad.materialdialogs.MaterialDialog
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.mikepenz.materialdrawer.Drawer
import kotlinx.android.synthetic.main.controller_home.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 29/05/2017.
 *
 * TODO: Reimplement TapTargetView
 */
class HomeController : BaseController(), HomeContract.View {
    @set:JvmName("setDrawer_") var drawer: Drawer? = null
    @Inject lateinit var context: Context
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: HomeEpxController
    @Inject lateinit var presenter: HomePresenter
    @Inject lateinit var imageViewAnimator: ImageViewAnimator
    @Inject lateinit var sharedPrefsManager: SharedPrefsManager
    lateinit var recyclerView: MyRecyclerView
    lateinit var toolbar: Toolbar
    lateinit var ivLoading: ImageView
    lateinit var lytLoading: ConstraintLayout
    lateinit var lytError: ConstraintLayout

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .mainComponentBuilder()
                .mainActivityModule(HomeModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_home, container, false)
        setupComponent(App.appComponent)
        recyclerView = view.recyclerView
        toolbar = view.toolbar
        ivLoading = view.ivLoading
        lytLoading = view.lytLoading
        lytError = view.lytError
        toolbar.inflateMenu(R.menu.options_menu)
        toolbar.title = "Home"
        view.btnError.setOnClickListener { presenter.connectAndBuildNavigationDrawer(toolbar) }
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.loaded), "onResume", "1")
        if (drawer == null)
            presenter.connectAndBuildNavigationDrawer(toolbar)
        else {
            setupRecyclerView()
            presenter.buildViewedReleases()
//            presenter.buildRecommendations()
        }
    }

    override fun handleBack(): Boolean {
        if (drawer?.isDrawerOpen as Boolean)
            drawer!!.closeDrawer()
        else
            MaterialDialog.Builder(activity!!)
                    .title("Quit")
                    .content("Really quit?")
                    .negativeText("Cancel")
                    .positiveText("Quit")
                    .onPositive { dialog, _ ->
                        dialog.dismiss()
                        activity!!.finishAndRemoveTask()
                    }
                    .show()
        return true
    }

    override fun retryHistory() {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.error), context.getString(R.string.clicked), "retryHistory", "1")
        presenter.buildViewedReleases()
    }

    override fun retryRecommendations() {
        presenter.showLoadingRecommendations(true)
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.error), context.getString(R.string.clicked), "retryRecommendations", "1")
        presenter.buildRecommendations()
    }

    override fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = controller.adapter
        controller.requestModelBuild()
        showLoading(false)
    }

    override fun displayOrder(id: String) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "order", "1")
        router.pushController(RouterTransaction.with(OrderController(id))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun displayOrdersActivity(username: String) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "All orders", "1")
        router.pushController(RouterTransaction.with(SingleListController(R.string.orders, username))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun displayListingsActivity(username: String) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "All listings", "1")
        router.pushController(RouterTransaction.with(SingleListController(R.string.selling, username))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun displayListing(listingId: String, title: String, username: String, s: String, sellerUsername: String) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "listing", "1")
        router.pushController(RouterTransaction.with(MarketplaceController(listingId, title, s, sellerUsername))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun displayRelease(releaseName: String, id: String) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.recently_viewed_release), context.getString(R.string.clicked), releaseName, "1")
        router.pushController(RouterTransaction.with(ReleaseController(releaseName, id))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun learnMore() {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.learn_more), context.getString(R.string.clicked), "recommendations learn more", "1")
        MaterialDialog.Builder(activity!!)
                .content(context.getString(R.string.learn_more_content))
                .negativeText("Dismiss")
                .show()
    }

    override fun showLoading(b: Boolean) {
        if (b) {
            //            lytMainContent.setVisibility(View.GONE);
            imageViewAnimator.rotateImage(ivLoading)
            lytLoading.visibility = View.VISIBLE
            displayError(false)
        } else {
            //            lytMainContent.setVisibility(View.VISIBLE);
            ivLoading.clearAnimation()
            lytLoading.visibility = View.GONE
        }
    }

    override fun displayError(b: Boolean) {
        if (b) {
            showLoading(false)
            lytError.visibility = View.VISIBLE
        } else
            lytError.visibility = View.GONE
    }

    override fun retry() {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "Retry", "1")
        presenter.retry()
    }

    override fun setDrawer(buildNavigationDrawer: Drawer?) {
        drawer = buildNavigationDrawer
        if (!sharedPrefsManager.isOnBoardingCompleted)
            TapTargetSequence(activity!!)
                    .targets(
                            TapTarget.forToolbarMenuItem(toolbar, R.id.search, "Search Discogs", "This is where the magic happens")
                                    .targetCircleColor(R.color.colorAccent)
                                    .cancelable(false),
                            TapTarget.forToolbarNavigationIcon(toolbar, "Navigation Drawer", "View your Wantlist and Collection")
                                    .targetCircleColor(R.color.colorAccent)
                                    .cancelable(false))
                    .listener(object : TapTargetSequence.Listener {
                        override fun onSequenceFinish() {
                            drawer?.openDrawer()
                            sharedPrefsManager.setOnboardingCompleted(applicationContext!!.getString(R.string.onboarding_completed))
                        }

                        override fun onSequenceStep(lastTarget: TapTarget, ye: Boolean) {}

                        override fun onSequenceCanceled(lastTarget: TapTarget) {}
                    }).start()
        displayError(false)
        setupRecyclerView()
        presenter.buildViewedReleases()
        presenter.buildRecommendations()
    }
}