package bj.vinylbrowser.main

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
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.mikepenz.materialdrawer.Drawer
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 29/05/2017.
 *
 * TODO: Reimplement TapTargetView
 */
class MainController : BaseController(), MainContract.View {
    @set:JvmName("setDrawer_") var drawer: Drawer? = null
    @Inject lateinit var context: Context
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: MainEpxController
    @Inject lateinit var presenter: MainPresenter
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
                .mainActivityModule(MainModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.content_main, container, false)
        setupComponent(App.appComponent)
        ButterKnife.bind(this, view)
        recyclerView = view.recyclerView
        toolbar = view.toolbar
        ivLoading = view.ivLoading
        lytLoading = view.lytLoading
        lytError = view.lytError
        toolbar.inflateMenu(R.menu.options_menu)
        toolbar.title = "Home"
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.loaded), "onResume", "1")
        if (drawer == null)
            presenter.connectAndBuildNavigationDrawer(toolbar)
        else
            setupRecyclerView()
        presenter.buildRecommendations()
        presenter.buildViewedReleases()
    }

    override fun handleBack(): Boolean {
        if (drawer?.isDrawerOpen as Boolean)
            drawer?.closeDrawer()
        else
            MaterialDialog.Builder(context)
                    .title("Quit")
                    .content("Really quit?")
                    .negativeText("Cancel")
                    .positiveText("Quit")
                    .onPositive { dialog, _ ->
                        dialog.dismiss()
//                        finishAndRemoveTask()
                    }
                    .show()
        return super.handleBack()
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

    override fun displayOrder(id: String?) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "order", "1")
//        startActivity(OrderActivity.createIntent(this, id))
    }

    override fun displayOrdersActivity(username: String?) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "All orders", "1")
//        startActivity(SingleListActivity.createIntent(this, R.string.orders, username))
    }

    override fun displayListingsActivity(username: String?) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "All listings", "1")
//        startActivity(SingleListActivity.createIntent(this, R.string.selling, username))
    }

    override fun displayListing(listingId: String?, username: String?, title: String?, s: String?, username1: String?) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.main_activity), context.getString(R.string.clicked), "listing", "1")
//        startActivity(MarketplaceListingActivity.createIntent(this, listingId, title, artist, seller))
    }

    override fun displayRelease(releaseName: String?, id: String?) {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.recently_viewed_release), context.getString(R.string.clicked), releaseName, "1")
//        startActivity(ReleaseActivity.createIntent(this, releaseName, id))
    }

    override fun learnMore() {
        tracker.send(context.getString(R.string.main_activity), context.getString(R.string.learn_more), context.getString(R.string.clicked), "recommendations learn more", "1")
        MaterialDialog.Builder(context)
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
        displayError(false)
        setupRecyclerView()
    }

    @OnClick(R.id.btnError)
    fun reconnectPressed() {
        presenter.connectAndBuildNavigationDrawer(toolbar)
    }
}