package bj.discogsbrowser.artistreleases.fragments;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.discogsbrowser.artistreleases.ArtistReleasesController;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.rxmodifiers.ArtistResultFunction;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 11/05/2017.
 */
public class ArtistReleasesFragmentPresenter implements ArtistReleasesFragmentContract.Presenter
{
    private final CompositeDisposable disposable;
    private final ArtistResultFunction artistResultFunction;
    private BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    private MySchedulerProvider mySchedulerProvider;
    private ArtistReleasesTransformer artistReleasesTransformer;
    private ArtistReleasesController controller;

    public ArtistReleasesFragmentPresenter(CompositeDisposable disposable, ArtistResultFunction artistResultFunction,
                                           BehaviorRelay<List<ArtistRelease>> behaviorRelay, MySchedulerProvider mySchedulerProvider, ArtistReleasesTransformer artistReleasesTransformer)
    {
        this.disposable = disposable;
        this.artistResultFunction = artistResultFunction;
        this.behaviorRelay = behaviorRelay;
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistReleasesTransformer = artistReleasesTransformer;
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
    public void bind(ArtistReleasesFragment fragment)
    {
        controller = fragment.getController();
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
