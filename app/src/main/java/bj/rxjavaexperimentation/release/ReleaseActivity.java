package bj.rxjavaexperimentation.release;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubeIntents;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.common.MyRecyclerView;
import bj.rxjavaexperimentation.marketplace.MarketplaceListingActivity;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class ReleaseActivity extends BaseActivity implements ReleaseContract.View
{
    @Inject ArtistsBeautifier artistsBeautifier;
    @Inject ReleasePresenter presenter;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        ReleaseComponent component = DaggerReleaseComponent.builder()
                .appComponent(appComponent)
                .releaseModule(new ReleaseModule(this))
                .build();

        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        presenter.setupRecyclerView(this, recyclerView, getIntent().getStringExtra("title"));
        presenter.getData(getIntent().getStringExtra("id"));
    }

    @Override
    public void displayListingInformation(String title, String artists, ScrapeListing scrapeListing)
    {
        Intent intent = new Intent(this, MarketplaceListingActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("artist", artists);
        intent.putExtra("seller", scrapeListing.getSellerName());
        intent.putExtra("sellerRating", scrapeListing.getSellerRating());
        intent.putExtra("id", scrapeListing.getMarketPlaceId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void launchYouTube(String youtubeId)
    {
//        new FinestWebView.Builder(this).show(uri);
        // Launch in the native YouTube app
        Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(this, youtubeId, false, false);
        startActivity(intent);
    }
}
