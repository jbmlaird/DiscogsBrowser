package bj.discogsbrowser.artistreleases;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 10/04/2017.
 */
public class ArtistReleasesPresenter implements ArtistReleasesContract.Presenter
{
    private ArtistReleasesContract.View view;
    private ArtistReleasesController controller;
    private DiscogsInteractor discogsInteractor;
    private CompositeDisposable disposable;
    private BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    private MySchedulerProvider mySchedulerProvider;
    private ArtistResultFunction artistResultFunction;
    private ArtistReleasesTransformer artistReleasesTransformer;

    @Inject
    public ArtistReleasesPresenter(ArtistReleasesContract.View view, ArtistReleasesController controller, DiscogsInteractor discogsInteractor,
                                   CompositeDisposable disposable, BehaviorRelay<List<ArtistRelease>> behaviorRelay,
                                   MySchedulerProvider mySchedulerProvider, ArtistResultFunction artistResultFunction, ArtistReleasesTransformer artistReleasesTransformer)
    {
        this.view = view;
        this.controller = controller;
        this.discogsInteractor = discogsInteractor;
        this.disposable = disposable;
        this.behaviorRelay = behaviorRelay;
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistResultFunction = artistResultFunction;
        this.artistReleasesTransformer = artistReleasesTransformer;
    }

    @Override
    public void setupRecyclerView(RecyclerView recyclerView, FragmentActivity activity)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(controller.getAdapter());
        controller.requestModelBuild();
    }

    @Override
    public void getArtistReleases(String id)
    {
        discogsInteractor.fetchArtistsReleases(id)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(behaviorRelay, error ->
                {
                    error.printStackTrace();
                    controller.setError("Unable to fetch Artist Releases");
                });
    }

    @Override
    public void connectToBehaviorRelay(String searchFilter)
    {
        artistResultFunction.setParameterToMapTo(searchFilter);
        disposable.add(
                behaviorRelay
                        .map(artistResultFunction)
                        .flatMap(artistReleases1 ->
                                Single.just(artistReleases1)
                                        .compose(artistReleasesTransformer)
                                        .toObservable())
                        .map(artistReleases1 ->
                                artistReleases1)
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(artistReleases1 ->
                                        controller.setItems(artistReleases1),
                                error ->
                                        controller.setError("Unable to fetch Artist Releases")));
    }

    @Override
    public void setupFilter()
    {
        view.filterIntent().subscribe(filterText ->
        {
            artistReleasesTransformer.setFilterText(filterText);
            if (behaviorRelay.getValue() != null && behaviorRelay.getValue().size() != 0)
                behaviorRelay.accept(behaviorRelay.getValue());
        });
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
