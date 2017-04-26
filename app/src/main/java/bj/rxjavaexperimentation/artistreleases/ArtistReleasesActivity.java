package bj.rxjavaexperimentation.artistreleases;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artist.ArtistActivity;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.label.LabelActivity;
import bj.rxjavaexperimentation.master.MasterActivity;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.release.ReleaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 10/04/2017.
 */
public class ArtistReleasesActivity extends BaseActivity implements ArtistReleasesContract.View
{
    public static ArtistReleasesComponent component;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @Inject DiscogsInteractor discogsInteractor;
    @Inject ArtistReleasesPresenter presenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        component = DaggerArtistReleasesComponent.builder()
                .appComponent(appComponent)
                .artistReleasesModule(new ArtistReleasesModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_releases);
        ButterKnife.bind(this);
        presenter.setupViewPager(tabLayout, viewPager, getSupportFragmentManager());
        presenter.getArtistReleases(getIntent().getStringExtra("id"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
    }

    @Override
    public void launchDetailedActivity(String type, String title, String id)
    {
        Intent intent = null;
        switch (type)
        {
            case "release":
                intent = new Intent(this, ReleaseActivity.class);
                break;
            case "label":
                intent = new Intent(this, LabelActivity.class);
                break;
            case "artist":
                intent = new Intent(this, ArtistActivity.class);
                break;
            case "master":
                intent = new Intent(this, MasterActivity.class);
                break;
        }
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
