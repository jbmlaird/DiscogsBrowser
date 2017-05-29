package bj.vinylbrowser.label

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.customviews.MyRecyclerView
import bj.vinylbrowser.release.ReleaseActivity
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 29/05/2017.
 */
class LabelController : BaseController(), LabelContract.View {
    @Inject lateinit var presenter: LabelPresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: LabelEpxController
    lateinit var recyclerView: MyRecyclerView
    lateinit var toolbar: Toolbar

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .labelComponentBuilder()
                .labelActivityModule(LabelModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.activity_recyclerview, container, false)
        recyclerView = view.recyclerView
        toolbar = view.toolbar
        setupRecyclerView(recyclerView, controller, args.getString("title"))
        presenter.fetchReleaseDetails(args.getString("id"))
        return view
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
        presenter.fetchReleaseDetails(args.getString("id"))
    }

    //TODO: Move over to Conductor
    override fun displayRelease(id: String?, title: String?) {
        tracker.send(applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.label_activity), applicationContext?.getString(R.string.clicked), "labelRelease", "1")
        startActivity(ReleaseActivity.createIntent(applicationContext, title, id))
    }
}