package bj.vinylbrowser.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.MainComponent
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import kotlinx.android.synthetic.main.controller_order.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class OrderController(val id: String) : BaseController(), OrderContract.View {
    @Inject lateinit var presenter: OrderPresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: OrderEpxController

    constructor(args: Bundle) : this(args.getString("id"))

    override fun setupComponent(mainComponent: MainComponent) {
        mainComponent
                .orderComponentBuilder()
                .orderModule(OrderModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent((activity as MainActivity).mainComponent)
        val view = inflater.inflate(R.layout.controller_order, container, false)
        setupToolbar(view.toolbar, id)
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