package bj.vinylbrowser.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class OrderController(val id: String) : BaseController(), OrderContract.View {
    @Inject lateinit var presenter: OrderPresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: OrderEpxController

    constructor(args: Bundle) : this(args.getString("id"))

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .orderComponentBuilder()
                .orderModule(OrderModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent(App.appComponent)
        val view = inflater.inflate(R.layout.activity_order, container, false)
        setupToolbar(view.toolbar, "")
        setupRecyclerView(view.recyclerView, controller)
        presenter.fetchOrderDetails(id)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(applicationContext!!.getString(R.string.order_activity), applicationContext!!.getString(R.string.order_activity), applicationContext!!.getString(R.string.loaded), "onResume", "1")
    }

    override fun retry() {
        tracker.send(applicationContext!!.getString(R.string.order_activity), applicationContext!!.getString(R.string.order_activity), applicationContext!!.getString(R.string.clicked), "retry", "1")
        presenter.fetchOrderDetails(id)
    }
}