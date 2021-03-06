package bj.vinylbrowser.artistreleases

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import bj.vinylbrowser.R
import bj.vinylbrowser.artist.ArtistController
import bj.vinylbrowser.artistreleases.child.MyRouterPagerAdapter
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.label.LabelController
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.MainComponent
import bj.vinylbrowser.master.MasterController
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.controller_artist_releases.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class ArtistReleasesController(val title: String, val id: String) : BaseController(), ArtistReleasesContract.View {
    companion object {
        var component: ArtistReleasesComponent? = null
    }

    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var presenter: ArtistReleasesPresenter
    lateinit var etFilter: EditText

    constructor(args: Bundle) : this(args.getString("title"), args.getString("id"))

    override fun setupComponent(mainComponent: MainComponent) {
        component = mainComponent
                .artistReleasesComponentBuilder()
                .artistReleasesModule(ArtistReleasesModule(this))
                .build()
        component!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent((activity as MainActivity).mainComponent)
        val view = inflater.inflate(R.layout.controller_artist_releases, container, false)
        setupViewPager(view.viewpager, view.tabLayout)
        etFilter = view.etFilter
        presenter.setupFilter()
        presenter.fetchArtistReleases(id)
        setupToolbar(view.toolbar, title)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(applicationContext!!.getString(R.string.artist_releases_activity), applicationContext!!.getString(R.string.artist_releases_activity), applicationContext!!.getString(R.string.loaded), "onResume", "1")
        if (view.viewpager.adapter == null)
            setupViewPager(view.viewpager, view.tabLayout)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        component = null // leaks otherwise and is only used by its fragments
    }

    override fun launchDetailedActivity(type: String, title: String, id: String) {
        tracker.send(applicationContext!!.getString(R.string.artist_releases_activity), applicationContext!!.getString(R.string.artist_releases_activity), applicationContext!!.getString(R.string.clicked), "retry", "1")
        when (type) {
            "release" -> router.pushController(RouterTransaction.with(ReleaseController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler())
                    .tag("ReleaseController"))
            "label" -> router.pushController(RouterTransaction.with(LabelController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler())
                    .tag("LabelController"))
            "artist" -> router.pushController(RouterTransaction.with(ArtistController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler())
                    .tag("ArtistController"))
            "master" -> router.pushController(RouterTransaction.with(MasterController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler())
                    .tag("MasterController"))
        }
    }

    override fun filterIntent(): Observable<CharSequence> {
        return RxTextView.textChanges(etFilter)
                .skipInitialValue()
    }

    private fun setupViewPager(viewPager: ViewPager, tabLayout: TabLayout) {
        viewPager.adapter = MyRouterPagerAdapter(this)
        tabLayout.setupWithViewPager(viewPager)
    }
}