package bj.vinylbrowser.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.model.version.MasterVersion
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import kotlinx.android.synthetic.main.controller_recyclerview.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class MasterController(val title: String, val id: String) : BaseController(), MasterContract.View {
    @Inject lateinit var presenter: MasterPresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: MasterEpxController

    constructor(args: Bundle) : this(args.getString("title"), args.getString("id"))

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .masterComponentBuilder()
                .masterActivityModule(MasterModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent(App.appComponent)
        val view = inflater.inflate(R.layout.controller_recyclerview, container, false)
        setupToolbar(view.toolbar, "")
        setupRecyclerView(view.recyclerView, controller)
        controller.setTitle(title)
        presenter.fetchArtistDetails(id)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(applicationContext!!.getString(R.string.master_activity), applicationContext!!.getString(R.string.master_activity),
                applicationContext!!.getString(R.string.loaded), "onResume", "1")
        if (view.recyclerView.adapter == null)
            setupRecyclerView(view.recyclerView, controller)
    }

    override fun displayRelease(title: String, id: String) {
        tracker.send(applicationContext!!.getString(R.string.master_activity), applicationContext!!.getString(R.string.master_activity),
                applicationContext!!.getString(R.string.clicked), "release", "1")
        router.pushController(RouterTransaction.with(ReleaseController(title, id))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun retry() {
        tracker.send(applicationContext!!.getString(R.string.master_activity), applicationContext!!.getString(R.string.master_activity),
                applicationContext!!.getString(R.string.clicked), "retry", "1")
        presenter.fetchArtistDetails(id)
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        controller.setMaster(savedViewState.getParcelable("master"))
        controller.setMasterVersions(savedViewState.getParcelableArrayList("masterVersions"))
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        outState.putParcelable("master", controller.master)
        outState.putParcelableArrayList("masterVersions", controller.masterVersions as ArrayList<MasterVersion>)
        super.onSaveViewState(view, outState)
    }
}