package bj.rxjavaexperimentation.artistreleases;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.artistreleases.fragments.ArtistReleasesFragment;
import bj.rxjavaexperimentation.discogs.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;

/**
 * Created by Josh Laird on 10/04/2017.
 */
public class ArtistReleasesPresenter implements ArtistReleasesContract.Presenter
{
    private ArtistReleasesContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private BehaviorRelay<List<ArtistRelease>> behaviorRelay;

    @Inject
    public ArtistReleasesPresenter(ArtistReleasesContract.View view, SearchDiscogsInteractor searchDiscogsInteractor, BehaviorRelay<List<ArtistRelease>> behaviorRelay)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.behaviorRelay = behaviorRelay;
    }

    @Override
    public void getArtistReleases(String id)
    {
        searchDiscogsInteractor.getArtistsReleases(id, behaviorRelay);
    }

    @Override
    public void setupViewPager(TabLayout tabLayout, ViewPager viewPager, FragmentManager supportFragmentManager)
    {
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(supportFragmentManager);
        ArrayList<Pair> fragmentValues = new ArrayList<>();
        fragmentValues.add(Pair.create("Main", "Releases"));
        fragmentValues.add(Pair.create("Remix", "Remixes"));
        fragmentValues.add(Pair.create("UnofficialRelease", "Unofficial"));
        addFragmentsToViewPager(viewPagerAdapter, fragmentValues);
        viewPager.setAdapter(viewPagerAdapter);
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
}
