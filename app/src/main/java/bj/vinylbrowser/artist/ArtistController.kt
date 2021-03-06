package bj.vinylbrowser.artist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.R
import bj.vinylbrowser.artistreleases.ArtistReleasesController
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.MainComponent
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import kotlinx.android.synthetic.main.controller_recyclerview.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 29/05/2017.
 */
class ArtistController(val title: String, val id: String) : BaseController(), ArtistContract.View {
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var presenter: ArtistPresenter
    @Inject lateinit var epxController: ArtistEpxController

    constructor(args: Bundle) : this(args.getString("title"), args.getString("id"))

    override fun setupComponent(mainComponent: MainComponent) {
        mainComponent
                .artistComponentBuilder()
                .artistModule(ArtistModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_recyclerview, container, false)
        setupComponent((activity as MainActivity).mainComponent)
        setupToolbar(view.toolbar, "")
        setupRecyclerView(view.recyclerView, epxController)
        epxController.setTitle(title)
        epxController.requestModelBuild()
        presenter.fetchReleaseDetails(id)
        return view
    }

    override fun openLink(url: String?) {
        tracker.send(applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.clicked), url, "1")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity?.startActivity(intent)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if (view.recyclerView.adapter == null)
            setupRecyclerView(view.recyclerView, epxController)
        tracker.send(applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.loaded), "onResume", "1")
    }

    override fun retry() {
        tracker.send(applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.clicked), "retry", "1")
        presenter.fetchReleaseDetails(id)
    }

    override fun showMemberDetails(name: String?, id: String?) {
        tracker.send(applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.clicked), "Show member details", "1")
        router.pushController(RouterTransaction.with(ArtistController(name!!, id!!))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler())
                .tag("ArtistController"))
    }

    override fun showArtistReleases(title: String, id: String) {
        tracker.send(applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.artist_activity), applicationContext?.getString(R.string.clicked), "show artist releases", "1")
        router.pushController(RouterTransaction.with(ArtistReleasesController(title, id))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler())
                .tag("ArtistReleasesController"))
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        epxController.setArtist(savedViewState.getParcelable("artist"))
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        outState.putParcelable("artist", epxController.artistResult)
        super.onSaveViewState(view, outState)
    }
}