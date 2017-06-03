package bj.vinylbrowser.release

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.label.LabelController
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.MainComponent
import bj.vinylbrowser.marketplace.MarketplaceController
import bj.vinylbrowser.model.listing.ScrapeListing
import bj.vinylbrowser.model.release.Release
import bj.vinylbrowser.utils.ArtistsBeautifier
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import kotlinx.android.synthetic.main.controller_recyclerview.view.*
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class ReleaseController(val title: String, val id: String) : BaseController(), ReleaseContract.View {
    @Inject lateinit var artistsBeautifier: ArtistsBeautifier
    @Inject lateinit var presenter: ReleasePresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var epxController: ReleaseEpxController

    constructor(args: Bundle) : this(args.getString("title"), args.getString("id"))

    override fun setupComponent(mainComponent: MainComponent) {
        mainComponent
                .releaseComponentBuilder()
                .releaseModule(ReleaseModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent((activity as MainActivity).mainComponent)
        val view = inflater.inflate(R.layout.controller_recyclerview, container, false)
        setupToolbar(view.toolbar, "")
        setupRecyclerView(view.recyclerView, epxController)
        epxController.setTitle(title)
        epxController.requestModelBuild()
        presenter.fetchReleaseDetails(id)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.loaded), "onResume", "1")
        if (view.recyclerView.adapter == null) {
            setupRecyclerView(view.recyclerView, epxController)
        }
    }

    override fun retry() {
        tracker.send(applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.clicked), "retryRelease", "1")
        presenter.fetchReleaseDetails(id)
    }

    override fun displayListingInformation(title: String, subtitle: String, scrapeListing: ScrapeListing) {
        tracker.send(applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.clicked), "setListing", "1")
        router.pushController(RouterTransaction.with(MarketplaceController(title, scrapeListing.marketPlaceId, subtitle, scrapeListing.sellerName))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler())
                .tag("MarketplaceController"))
    }

    override fun displayLabel(title: String?, id: String?) {
        tracker.send(applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.clicked), "displayLabel", "1")
        router.pushController(RouterTransaction.with(LabelController(title!!, id!!))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler())
                .tag("LabelController"))
    }

    override fun retryCollectionWantlist() {
        tracker.send(applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.clicked), "retryWantlistCollection", "1")
        presenter.checkCollectionWantlist()
    }

    override fun retryListings() {
        try {
            tracker.send(applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.release_activity), applicationContext!!.getString(R.string.clicked), "retryListings", "1")
            presenter.fetchReleaseListings(id)
        } catch (e: IOException) {
        }
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        epxController.setRelease(savedViewState.getParcelable<Release>("release"))
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        outState.putParcelable("release", epxController.release)
        super.onSaveViewState(view, outState)
    }
}