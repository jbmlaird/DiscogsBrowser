package bj.discogsbrowser.artistreleases;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.artist.ArtistActivity;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.label.LabelActivity;
import bj.discogsbrowser.master.MasterActivity;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 10/04/2017.
 */
public class ArtistReleasesActivity extends BaseActivity implements ArtistReleasesContract.View
{
    public static ArtistReleasesComponent component;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.etFilter) EditText etFilter;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @Inject AnalyticsTracker tracker;
    @Inject DiscogsInteractor discogsInteractor;
    @Inject ArtistReleasesPresenter presenter;
    @Inject ImageViewAnimator imageViewAnimator;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        component = DaggerArtistReleasesComponent.builder()
                .appComponent(appComponent)
                .artistReleasesModule(new ArtistReleasesModule(this))
                .build();
        component.inject(this);
    }

    public static Intent createIntent(Context context, String title, String id)
    {
        Intent intent = new Intent(context, ArtistReleasesActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_releases);
        unbinder = ButterKnife.bind(this);
        presenter.setupViewPager(tabLayout, viewPager, getSupportFragmentManager());
        presenter.getArtistReleases(getIntent().getStringExtra("id"));
        setupToolbar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public void launchDetailedActivity(String type, String title, String id)
    {
        tracker.send(getString(R.string.artist_releases_activity), getString(R.string.artist_releases_activity), getString(R.string.clicked), "retry", 1L);
        switch (type)
        {
            case "release":
                startActivity(ReleaseActivity.createIntent(this, title, id));
                break;
            case "label":
                startActivity(LabelActivity.createIntent(this, title, id));
                break;
            case "artist":
                startActivity(ArtistActivity.createIntent(this, title, id));
                break;
            case "master":
                startActivity(MasterActivity.createIntent(this, title, id));
                break;
        }
    }

    @Override
    public Observable<CharSequence> filterIntent()
    {
        return RxTextView.textChanges(etFilter);
    }

    /**
     * Required as CardView theme goes dark in Android 5.1. Will need to replace with Epoxy.
     *
     * @return
     */
    @Override
    public Context getContext()
    {
        return this;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.artist_releases_activity), getString(R.string.artist_releases_activity), getString(R.string.loaded), "onResume", 1L);
        super.onResume();
    }
}
