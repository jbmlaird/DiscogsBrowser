package bj.discogsbrowser.artist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.thefinestartist.finestwebview.FinestWebView;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.artistreleases.ArtistReleasesActivity;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.common.MyRecyclerView;
import bj.discogsbrowser.epoxy.common.BaseController;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 07/04/2017.
 */
public class ArtistActivity extends BaseActivity implements ArtistContract.View
{
    @BindView(R.id.recyclerView) MyRecyclerView rvDetailed;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject AnalyticsTracker tracker;
    @Inject ArtistPresenter presenter;
    @Inject ArtistController controller;

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
        setupRecyclerView(rvDetailed, controller, getIntent().getStringExtra("title"));
        presenter.getReleaseAndLabelDetails(getIntent().getStringExtra("id"));
    }

    @Override
    public void showMemberDetails(String name, String id)
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), "Show member details", 1L);
        startActivity(createIntent(this, name, id));
    }

    @Override
    public void showLink(String link)
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), link, 1L);
        new FinestWebView.Builder(this).show(link);
    }

    @Override
    public void showArtistReleases(String title, String id)
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), "show artist releases", 1L);
        startActivity(ArtistReleasesActivity.createIntent(this, title, id));
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.loaded), "onResume", 1L);
        super.onResume();
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.artist_activity), getString(R.string.artist_activity), getString(R.string.clicked), "retry", 1L);
        presenter.getReleaseAndLabelDetails(getIntent().getStringExtra("id"));
    }

    private void setupRecyclerView(RecyclerView rvDetailed, BaseController controller, String title)
    {
        super.setupRecyclerView(rvDetailed, controller);
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
