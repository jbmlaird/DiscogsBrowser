package bj.rxjavaexperimentation.artistreleases;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.artistreleases.fragments.ArtistReleasesAdapter;
import bj.rxjavaexperimentation.artistreleases.fragments.ArtistReleasesFragment;
import bj.rxjavaexperimentation.artistreleases.fragments.ArtistResultFunction;
import bj.rxjavaexperimentation.artistreleases.fragments.ViewPagerAdapter;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@Singleton
public class ArtistReleasesPresenter implements ArtistReleasesContract.Presenter
{
    private Context context;
    private ArtistReleasesContract.View view;
    private DiscogsInteractor discogsInteractor;
    private CompositeDisposable compositeDisposable;
    private BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    private MySchedulerProvider mySchedulerProvider;
    private ArtistResultFunction artistResultFunction;

    @Inject
    public ArtistReleasesPresenter(Context context, ArtistReleasesContract.View view, DiscogsInteractor discogsInteractor, CompositeDisposable compositeDisposable,
                                   BehaviorRelay<List<ArtistRelease>> behaviorRelay, MySchedulerProvider mySchedulerProvider,
                                   ArtistResultFunction artistResultFunction)
    {
        this.context = context;
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.compositeDisposable = compositeDisposable;
        this.behaviorRelay = behaviorRelay;
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistResultFunction = artistResultFunction;
    }

    @Override
    public void getArtistReleases(String id)
    {
        compositeDisposable.add(discogsInteractor.fetchArtistsReleases(id)
                .subscribe(behaviorRelay));
    }

    @Override
    public void setupViewPager(TabLayout tabLayout, ViewPager viewPager, FragmentManager supportFragmentManager)
    {
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(supportFragmentManager);
        ArrayList<Pair> fragmentValues = new ArrayList<>();
        fragmentValues.add(Pair.create("master", "Masters"));
        fragmentValues.add(Pair.create("release", "Releases"));
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

    @Override
    public ArtistReleasesAdapter setupRecyclerView(RecyclerView recyclerView, FragmentActivity activity)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        ArtistReleasesAdapter rvReleasesAdapter = new ArtistReleasesAdapter(this, context);
        recyclerView.setAdapter(rvReleasesAdapter);
        return rvReleasesAdapter;
    }

    @Override
    public void connectToBehaviorRelay(Consumer<List<ArtistRelease>> consumer, String searchFilter)
    {
        behaviorRelay
                .map(artistResultFunction.map(searchFilter))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(consumer);
    }

    @Override
    public void launchDetailedActivity(String type, String title, String id)
    {
        view.launchDetailedActivity(type, title, id);
    }

    @Override
    public void setupFilter(Consumer<CharSequence> filterConsumer)
    {
        view.filterIntent().subscribe(filterConsumer);
    }

    @Override
    public void unsubscribe()
    {
        compositeDisposable.dispose();
    }
}
