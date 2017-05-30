package bj.vinylbrowser.singlelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.artist.ArtistController
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.label.LabelController
import bj.vinylbrowser.marketplace.MarketplaceController
import bj.vinylbrowser.master.MasterController
import bj.vinylbrowser.order.OrderController
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_single_list.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class SingleListController(val type: Int, val username: String)
    : BaseController(), SingleListContract.View {
    @Inject lateinit var presenter: SingleListPresenter
    @Inject lateinit var imageViewAnimator: ImageViewAnimator
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: SingleListEpxController
    lateinit var etFilter: EditText

    constructor(args: Bundle) : this(args.getInt("type"), args.getString("username"))

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .singleListComponentBuilder()
                .singleModule(SingleListModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent(App.appComponent)
        val view = inflater.inflate(R.layout.activity_single_list, container, false)
        setupToolbar(view.toolbar, "")
        setupRecyclerView(view.recyclerView, controller)
        etFilter = view.etFilter
        presenter.setupFilterSubscription()
        controller.requestModelBuild()
        presenter.getData(type, username)
        return view
    }

    override fun launchDetailedActivity(type: String, title: String, id: String) {
        tracker.send(applicationContext!!.getString(R.string.single_list_activity), type, applicationContext!!.getString(R.string.clicked), "detailedActivity" + type, "1")
        when (type) {
            "release" -> router.pushController(RouterTransaction.with(ReleaseController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "label" -> router.pushController(RouterTransaction.with(LabelController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "artist" -> router.pushController(RouterTransaction.with(ArtistController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "master" -> router.pushController(RouterTransaction.with(MasterController(title, id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "listing" -> router.pushController(RouterTransaction.with(MarketplaceController(title, id, "", ""))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "order" -> router.pushController(RouterTransaction.with(OrderController(id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
        }
    }

    /**
     * Exposes an Observable that watches the EditText's changes.
     *
     * @return Observable.
     */
    override fun filterIntent(): Observable<CharSequence> {
        return RxTextView.textChanges(etFilter)
    }
}