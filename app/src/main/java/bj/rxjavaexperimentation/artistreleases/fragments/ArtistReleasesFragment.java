package bj.rxjavaexperimentation.artistreleases.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesActivity;
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesPresenter;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.schedulerprovider.SchedulerProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 11/04/2017.
 */

public class ArtistReleasesFragment extends Fragment
{
    private static final String TAG = "MixesFragment";
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @Inject BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    @Inject ArtistResultFunction artistResultFunction;
    @Inject MySchedulerProvider mySchedulerProvider;
    @Inject ArtistReleasesPresenter presenter;
    private RecyclerViewReleasesAdapter rvReleasesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ArtistReleasesActivity.component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        connectToBehaviorRelay(behaviorRelay, artistResultFunction, getConsumer(), mySchedulerProvider);
        rvReleasesAdapter = presenter.setupRecyclerView(recyclerView, getActivity());
        return view;
    }

    @VisibleForTesting
    public void connectToBehaviorRelay(BehaviorRelay<List<ArtistRelease>> behaviorRelay, ArtistResultFunction artistResultFunction, Consumer<List<ArtistRelease>> consumer, SchedulerProvider mySchedulerProvider)
    {
        behaviorRelay
                .map(artistResultFunction.map(getArguments().getString("map")))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(consumer);
    }

    private Consumer<List<ArtistRelease>> getConsumer()
    {
        return o ->
        {
            rvReleasesAdapter.setRemixes(o);
            rvReleasesAdapter.notifyDataSetChanged();
        };
    }

    @VisibleForTesting
    public void setArtistResultFunction(ArtistResultFunction artistResultFunction)
    {
        this.artistResultFunction = artistResultFunction;
    }
}
