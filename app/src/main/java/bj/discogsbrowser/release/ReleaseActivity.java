package bj.discogsbrowser.release;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.customviews.MyRecyclerView;
import bj.discogsbrowser.label.LabelActivity;
import bj.discogsbrowser.marketplace.MarketplaceListingActivity;
import bj.discogsbrowser.model.listing.ScrapeListing;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 23/04/2017.
 * <p>
 * Displays {@link bj.discogsbrowser.model.release.Release} details.
 */
public class ReleaseActivity extends BaseActivity implements ReleaseContract.View
{
    @Inject ArtistsBeautifier artistsBeautifier;
    @Inject ReleasePresenter presenter;
    @Inject AnalyticsTracker tracker;
    @Inject ReleaseController controller;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .releaseComponentBuilder()
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
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.loaded), "onResume", 1);
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        setupRecyclerView(recyclerView, controller, getIntent().getStringExtra("title"));
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    @Override
    public void displayListingInformation(String title, String artists, ScrapeListing scrapeListing)
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "setListing", 1);
        startActivity(MarketplaceListingActivity.createIntent(this, scrapeListing.getMarketPlaceId(), title, artists, scrapeListing.getSellerName()));
    }

    @Override
    public void launchYouTube(String youtubeId)
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "launchYoutube", 1);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youtubeId)));
    }

    @Override
    public void displayLabel(String title, String id)
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "displayLabel", 1);
        startActivity(LabelActivity.createIntent(this, title, id));
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "retryRelease", 1);
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    @Override
    public void retryCollectionWantlist()
    {
        tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "retryWantlistCollection", 1);
        presenter.checkCollectionWantlist();
    }

    @Override
    public void retryListings()
    {
        try
        {
            tracker.send(getString(R.string.release_activity), getString(R.string.release_activity), getString(R.string.clicked), "retryListings", 1);
            presenter.fetchReleaseListings(getIntent().getStringExtra("id"));
        }
        catch (IOException e)
        {

        }
    }

    private void setupRecyclerView(MyRecyclerView recyclerView, ReleaseController controller, String title)
    {
        setupRecyclerView(recyclerView, controller);
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
