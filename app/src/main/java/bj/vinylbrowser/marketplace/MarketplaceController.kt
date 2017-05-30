package bj.vinylbrowser.marketplace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.content_main.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 30/05/2017.
 */
class MarketplaceController(val id: String, val title: String, val artist: String, val seller: String) :
        BaseController(), MarketplaceContract.View {
    @Inject lateinit var presenter: MarketplacePresenter
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var imageViewAnimator: ImageViewAnimator
    @Inject lateinit var controller: MarketplaceEpxController

    constructor(args: Bundle) : this(args.getString("id"), args.getString("title"),
            args.getString("artist"), args.getString("seller"))

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .marketplaceComponentBuilder()
                .marketplaceModule(MarketplaceModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setupComponent(App.appComponent)
        val view = inflater.inflate(R.layout.activity_marketplace_listing, container, false)
        setupToolbar(view.toolbar, id)
        presenter.getListingDetails(id)
        setupRecyclerView(view.recyclerView, controller)
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(applicationContext?.getString(R.string.main_activity), applicationContext?.getString(R.string.main_activity), applicationContext?.getString(R.string.loaded), "onResume", "1")
    }

    override fun retry() {
        presenter.getListingDetails(id)
    }

    override fun viewOnDiscogs(listingUri: String?) {
        tracker.send(applicationContext?.getString(R.string.marketplace_activity), applicationContext?.getString(R.string.marketplace_activity), applicationContext?.getString(R.string.loaded), "onResume", "1")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(listingUri))
        startActivity(intent)
    }

    override fun viewSellerShipping(shippingDetails: String, username: String) {
        tracker.send(applicationContext?.getString(R.string.marketplace_activity), applicationContext?.getString(R.string.marketplace_activity), applicationContext?.getString(R.string.clicked), "Seller Shipping Info", "1")
        MaterialDialog.Builder(activity!!)
                .content(shippingDetails)
                .title(username)
                .negativeText("Dismiss")
                .show()
    }
}