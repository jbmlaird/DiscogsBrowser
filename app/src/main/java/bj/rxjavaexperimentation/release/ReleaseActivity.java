package bj.rxjavaexperimentation.release;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.common.MyRecyclerView;
import bj.rxjavaexperimentation.label.LabelActivity;
import bj.rxjavaexperimentation.marketplace.MarketplaceListingActivity;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;
import bj.rxjavaexperimentation.utils.AnalyticsTracker;
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
    @Inject AnalyticsTracker tracker;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        DaggerReleaseComponent.builder()
                .appComponent(appComponent)
                .releaseModule(new ReleaseModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String title, String id)
    {
        Intent intent = new Intent(context, ReleaseActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.loaded), "onResume", 1L);
        super.onResume();
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
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "displayListing", 1L);
        startActivity(MarketplaceListingActivity.createIntent(this, scrapeListing.getMarketPlaceId(), title, artists, scrapeListing.getSellerName()));
    }

    @Override
    public void launchYouTube(String youtubeId)
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "launchYoutube", 1L);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youtubeId)));
    }

    @Override
    public void displayLabel(String title, String id)
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "displayLabel", 1L);
        startActivity(LabelActivity.createIntent(this, title, id));
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "retryRelease", 1L);
        presenter.getData(getIntent().getStringExtra("id"));
    }

    @Override
    public void retryCollectionWantlist()
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "retryWantlistCollection", 1L);
        presenter.retryCollectionWantlist();
    }

    @Override
    public void retryListings()
    {
        try
        {
            tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "retryListings", 1L);
            presenter.fetchReleaseListings(getIntent().getStringExtra("id"));
        }
        catch (IOException e)
        {

        }
    }
}
