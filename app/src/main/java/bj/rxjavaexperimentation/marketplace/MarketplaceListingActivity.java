package bj.rxjavaexperimentation.marketplace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.text.NumberFormat;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.user.UserDetails;
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
        MarketplaceComponent component = DaggerMarketplaceComponent.builder()
                .appComponent(appComponent)
                .marketplaceModule(new MarketplaceModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace_listing);
        ButterKnife.bind(this);
        setupActionBar(toolbar);
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
//                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .dontAnimate()
                .into(ivImage);
        tvItemName.setText(listing.getRelease().getDescription());
        tvSleeve.setText("{fa-inbox} " + listing.getSleeveCondition());
        tvMedia.setText("{fa-music} " + listing.getCondition());
        tvSeller.setText("Seller: " + listing.getSeller().getUsername());
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

    @OnClick(R.id.lytViewOnDiscogs)
    public void viewOnDiscogs()
    {
        new FinestWebView.Builder(this).show(listing.getUri());
    }

    @OnClick(R.id.lytViewSellerShipping)
    public void viewSellerShipping()
    {
        new MaterialDialog.Builder(this)
                .content(listing.getSeller().getShipping())
                .title(listing.getSeller().getUsername())
                .negativeText("Dismiss")
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

}
