package bj.discogsbrowser.marketplace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.customviews.MyRecyclerView;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 13/04/2017.
 * <p>
 * Activity to show listing information.
 */
public class MarketplaceListingActivity extends BaseActivity implements MarketplaceContract.View
{
    @Inject MarketplacePresenter presenter;
    @Inject AnalyticsTracker tracker;
    @Inject ImageViewAnimator imageViewAnimator;
    @Inject MarketplaceController controller;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .marketplaceComponentBuilder()
                .marketplaceModule(new MarketplaceModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String id, String title, String artist, String seller)
    {
        Intent intent = new Intent(context, MarketplaceListingActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("artist", artist);
        intent.putExtra("seller", seller);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace_listing);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar, getIntent().getStringExtra("id"));
        presenter.getListingDetails(getIntent().getStringExtra("id"));
        setupRecyclerView(recyclerView, controller);
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.loaded), "onResume", 1);
        super.onResume();
    }

    @Override
    public void viewOnDiscogs(String listingUri)
    {
        tracker.send(getString(R.string.marketplace_activity), getString(R.string.marketplace_activity), getString(R.string.loaded), "onResume", 1);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(listingUri));
        startActivity(intent);
    }

    @Override
    public void viewSellerShipping(String shippingDetails, String username)
    {
        tracker.send(getString(R.string.marketplace_activity), getString(R.string.marketplace_activity), getString(R.string.clicked), "Seller Shipping Info", 1);
        new MaterialDialog.Builder(this)
                .content(shippingDetails)
                .title(username)
                .negativeText("Dismiss")
                .show();
    }

    @Override
    public void retry()
    {
        presenter.getListingDetails(getIntent().getStringExtra("id"));
    }
}
