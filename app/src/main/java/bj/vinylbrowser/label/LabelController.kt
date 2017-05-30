package bj.vinylbrowser.label

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.customviews.MyRecyclerView
import bj.vinylbrowser.model.common.Label
import bj.vinylbrowser.model.labelrelease.LabelRelease
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import kotlinx.android.synthetic.main.controller_single_list.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 29/05/2017.
 */
class LabelController(val title: String, val id: String) : BaseController(), LabelContract.View {
    @Inject lateinit var presenter: LabelPresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: LabelEpxController
    lateinit var recyclerView: MyRecyclerView
    lateinit var toolbar: Toolbar

    constructor(args: Bundle) : this(args.getString("title"), args.getString("id"))

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .labelComponentBuilder()
                .labelActivityModule(LabelModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_recyclerview, container, false)
        setupComponent(App.appComponent)
        recyclerView = view.recyclerView
        setupToolbar(view.toolbar, "")
        setupRecyclerView(recyclerView, controller, title)
        presenter.fetchArtistDetails(id)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if (view.recyclerView.adapter == null)
            setupRecyclerView(view.recyclerView, controller, title)
    }

    private fun setupRecyclerView(recyclerView: MyRecyclerView?, controller: LabelEpxController, title: String?) {
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView?.adapter = controller.adapter
        controller.setTitle(title)
        controller.requestModelBuild()
    }

    override fun openLink(url: String?) {
        tracker.send(applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.clicked), url, "1")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun retry() {
        tracker.send(applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.clicked), "retry", "1")
        presenter.fetchArtistDetails(args.getString("id"))
    }

    override fun displayRelease(id: String, title: String) {
        tracker.send(applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.clicked), "labelRelease", "1")
        router.pushController(RouterTransaction.with(ReleaseController(title, id))
                .popChangeHandler(FadeChangeHandler())
                .pushChangeHandler(FadeChangeHandler()))
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        controller.setLabel(savedViewState.getParcelable<Label>("label"))
        controller.setLabelReleases(savedViewState.getParcelableArrayList("labelReleases"))
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        outState.putParcelable("label", controller.label)
        outState.putParcelableArrayList("labelReleases", controller.labelReleases as ArrayList<LabelRelease>)
        super.onSaveViewState(view, outState)
    }
}