package bj.vinylbrowser.artistreleases.fragments;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.vinylbrowser.artistreleases.ArtistReleaseBehaviorRelay;
import bj.vinylbrowser.artistreleases.ArtistReleasesEpxController;
import bj.vinylbrowser.model.artistrelease.ArtistRelease;
import bj.vinylbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.vinylbrowser.utils.rxmodifiers.ArtistResultFunction;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    private ArtistReleasesEpxController controller;

    public ArtistReleasesFragmentPresenter(CompositeDisposable disposable, ArtistResultFunction artistResultFunction,
                                           ArtistReleaseBehaviorRelay behaviorRelay, MySchedulerProvider mySchedulerProvider,
                                           ArtistReleasesTransformer artistReleasesTransformer)
    {
        this.disposable = disposable;
        this.artistResultFunction = artistResultFunction;
        this.behaviorRelay = behaviorRelay.getArtistReleaseBehaviorRelay();
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistReleasesTransformer = artistReleasesTransformer;
    }

    /**
     * Connects the Fragment to the {@link ArtistReleaseBehaviorRelay}.
     *
     * @param searchFilter master or release to filter the {@link ArtistRelease}s by.
     */
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
    public void bind(ArtistReleasesChildController controller)
    {
        this.controller = controller.getController();
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
