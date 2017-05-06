package bj.discogsbrowser.marketplace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.text.NumberFormat;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.user.UserDetails;
import bj.discogsbrowser.utils.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Josh Laird on 13/04/2017.
 */
public class MarketplaceListingActivity extends BaseActivity implements MarketplaceContract.View
{
    @Inject MarketplacePresenter presenter;
    @Inject AnalyticsTracker tracker;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvItemName) TextView tvItemName;
    @BindView(R.id.ivImage) CircleImageView ivImage;
    @BindView(R.id.tvSeller) TextView tvSeller;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @BindView(R.id.tvSellerRating) TextView tvSellerRating;
    @BindView(R.id.tvComments) TextView tvComments;
    @BindView(R.id.tvSleeve) IconTextView tvSleeve;
    @BindView(R.id.tvMedia) IconTextView tvMedia;
    private Listing listing;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        DaggerMarketplaceComponent.builder()
                .appComponent(appComponent)
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
        setupToolbar(toolbar);
        presenter.getListingDetails(getIntent().getStringExtra("id"));
        tvItemName.setText(getIntent().getStringExtra("title"));
        tvSeller.setText("Seller: " + getIntent().getStringExtra("seller"));
    }

    @Override
    public void displayListing(Listing listing)
    {
        this.listing = listing;
        Glide.with(this)
                .load(listing.getRelease().getThumbnail())
                .dontAnimate()
                .placeholder(R.drawable.ic_vinyl)
                .into(ivImage);
        tvItemName.setText(listing.getRelease().getDescription());
        tvSleeve.setText("{fa-inbox} " + listing.getSleeveCondition());
        tvMedia.setText("{fa-music} " + listing.getCondition());
        tvSeller.setText("Seller: " + listing.getSeller().getUsername());
//        tvSellerRating.setText("Seller rating: " + listing.getSeller().get);
        if (!listing.getComments().equals(""))
            tvComments.setText(listing.getComments());
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        tvPrice.setText(numberFormat.format(listing.getPrice().getValue()));
    }

    @Override
    public void updateUserDetails(UserDetails userDetails)
    {
        tvSellerRating.setText("Seller rating: " + userDetails.getSellerRating() + "%");
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.loaded), "onResume", 1L);
        super.onResume();
    }

    @OnClick(R.id.lytViewOnDiscogs)
    public void viewOnDiscogs()
    {
        tracker.send(getString(R.string.marketplace_activity), getString(R.string.marketplace_activity), getString(R.string.loaded), "onResume", 1L);
        new FinestWebView.Builder(this).show(listing.getUri());
    }

    @OnClick(R.id.lytViewSellerShipping)
    public void viewSellerShipping()
    {
        tracker.send(getString(R.string.marketplace_activity), getString(R.string.marketplace_activity), getString(R.string.clicked), "Seller Shipping Info", 1L);
        new MaterialDialog.Builder(this)
                .content(listing.getSeller().getShipping())
                .title(listing.getSeller().getUsername())
                .negativeText("Dismiss")
                .show();
    }
}
