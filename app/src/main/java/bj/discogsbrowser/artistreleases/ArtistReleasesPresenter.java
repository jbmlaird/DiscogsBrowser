package bj.discogsbrowser.artistreleases;

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
import com.jakewharton.rxrelay2.Relay;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.R;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesAdapter;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragment;
import bj.discogsbrowser.artistreleases.fragments.ArtistResultFunction;
import bj.discogsbrowser.artistreleases.fragments.ViewPagerAdapter;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    private CompositeDisposable disposable;
    private BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    private Relay<Throwable> throwableRelay;
    private MySchedulerProvider mySchedulerProvider;
    private ArtistResultFunction artistResultFunction;
    private AnalyticsTracker tracker;

    @Inject
    public ArtistReleasesPresenter(Context context, ArtistReleasesContract.View view, DiscogsInteractor discogsInteractor, CompositeDisposable disposable,
                                   BehaviorRelay<List<ArtistRelease>> behaviorRelay, Relay<Throwable> throwableRelay,
                                   MySchedulerProvider mySchedulerProvider, ArtistResultFunction artistResultFunction, AnalyticsTracker tracker)
    {
        this.context = context;
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.disposable = disposable;
        this.behaviorRelay = behaviorRelay;
        this.throwableRelay = throwableRelay;
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistResultFunction = artistResultFunction;
        this.tracker = tracker;
    }

    @Override
    public void getArtistReleases(String id)
    {
        discogsInteractor.fetchArtistsReleases(id)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(behaviorRelay, throwableRelay);
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
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                tracker.send(context.getString(R.string.artist_releases_activity), context.getString(R.string.artist_releases_activity), context.getString(R.string.clicked), "ViewPager", (long) position);
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
    public ArtistReleasesAdapter setupRecyclerView(RecyclerView recyclerView, FragmentActivity activity)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        ArtistReleasesAdapter rvReleasesAdapter = new ArtistReleasesAdapter(this, getViewContext());
        recyclerView.setAdapter(rvReleasesAdapter);
        return rvReleasesAdapter;
    }

    @Override
    public void connectToBehaviorRelay(Consumer<List<ArtistRelease>> consumer, Consumer<Throwable> throwableConsumer, String searchFilter)
    {
        disposable.add(
                behaviorRelay
                        .map(artistResultFunction.map(searchFilter))
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(consumer));

        disposable.add(
                throwableRelay
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(throwableConsumer));
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
    public Context getViewContext()
    {
        return view.getContext();
    }

    @Override
    public void unsubscribe()
    {
        disposable.clear();
    }

    @Override
    public void dispose()
    {
        disposable.dispose();
    }
}
