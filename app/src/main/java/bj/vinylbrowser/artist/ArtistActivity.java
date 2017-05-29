package bj.vinylbrowser.artist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.artistreleases.ArtistReleasesActivity;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.customviews.MyRecyclerView;
import bj.vinylbrowser.epoxy.common.BaseEpxController;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 07/04/2017.
 * <p>
 * Activity that displays Artist details.
 */
public class ArtistActivity extends BaseActivity implements ArtistContract.View
{
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject AnalyticsTracker tracker;
    @Inject ArtistPresenter presenter;
    @Inject ArtistEpxController controller;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .artistComponentBuilder()
                .artistModule(new ArtistModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String title, String id)
    {
        Intent intent = new Intent(context, ArtistActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        return intent;
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
    public void showMemberDetails(String name, String id)
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), "Show member details", "1");
        startActivity(createIntent(this, name, id));
    }

    @Override
    public void openLink(String link)
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), link, "1");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    @Override
    public void showArtistReleases(String title, String id)
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), "show artist releases", "1");
        startActivity(ArtistReleasesActivity.createIntent(this, title, id));
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.loaded), "onResume", "1");
        super.onResume();
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), "retry", "1");
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    private void setupRecyclerView(RecyclerView rvDetailed, BaseEpxController controller, String title)
    {
        super.setupRecyclerView(rvDetailed, controller);
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
