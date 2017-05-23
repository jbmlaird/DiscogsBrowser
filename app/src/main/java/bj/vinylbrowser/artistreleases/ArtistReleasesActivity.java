package bj.vinylbrowser.artistreleases;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.artist.ArtistActivity;
import bj.vinylbrowser.artistreleases.fragments.ArtistReleasesFragment;
import bj.vinylbrowser.artistreleases.fragments.ViewPagerAdapter;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.label.LabelActivity;
import bj.vinylbrowser.master.MasterActivity;
import bj.vinylbrowser.release.ReleaseActivity;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 10/04/2017.
 * <p>
 * Activity containing two {@link ArtistReleasesFragment} and a filter bar.
 */
public class ArtistReleasesActivity extends BaseActivity implements ArtistReleasesContract.View
{
    private static ArtistReleasesComponent component;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.etFilter) EditText etFilter;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @Inject AnalyticsTracker tracker;
    @Inject ArtistReleasesPresenter presenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        component = appComponent
                .artistReleasesComponentBuilder()
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
        setupViewPager();
        presenter.setupFilter();
        presenter.fetchArtistReleases(getIntent().getStringExtra("id"));
        setupToolbar(toolbar, getIntent().getStringExtra("title"));
    }

    @Override
    public void launchDetailedActivity(String type, String title, String id)
    {
        tracker.send(getString(R.string.artist_releases_activity), getString(R.string.artist_releases_activity), getString(R.string.clicked), "retry", "1");
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
        return RxTextView.textChanges(etFilter)
                .skipInitialValue();
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.artist_releases_activity), getString(R.string.artist_releases_activity), getString(R.string.loaded), "onResume", "1");
        super.onResume();
    }

    private void setupViewPager()
    {
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ArrayList<Pair> fragmentValues = new ArrayList<>();
        fragmentValues.add(Pair.create("master", "Masters"));
        fragmentValues.add(Pair.create("release", "Releases"));
        addFragmentsToViewPager(viewPagerAdapter, fragmentValues);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                tracker.send(getString(R.string.artist_releases_activity), getString(R.string.artist_releases_activity), getString(R.string.clicked), "ViewPager", String.valueOf(position));
                super.onPageSelected(position);
            }
        });
    }

    private void addFragmentsToViewPager(ViewPagerAdapter viewPagerAdapter, ArrayList<Pair> pairs)
    {
        for (Pair pair : pairs)
        {
            ArtistReleasesFragment artistReleasesFragment = new ArtistReleasesFragment();
            Bundle releasesBundle = new Bundle();
            releasesBundle.putString("map", String.valueOf(pair.first));
            artistReleasesFragment.setArguments(releasesBundle);
            viewPagerAdapter.addFragment(artistReleasesFragment, String.valueOf(pair.second));
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        component = null; // leaks otherwise and is only used by its fragments
    }

    public static ArtistReleasesComponent getComponent()
    {
        return component;
    }
}