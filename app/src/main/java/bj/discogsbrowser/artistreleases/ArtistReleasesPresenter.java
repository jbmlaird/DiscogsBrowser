package bj.discogsbrowser.artistreleases;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 10/04/2017.
 */
public class ArtistReleasesPresenter implements ArtistReleasesContract.Presenter
{
    private ArtistReleasesContract.View view;
    private DiscogsInteractor discogsInteractor;
    private ArtistReleasesController controller;
    private BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    private MySchedulerProvider mySchedulerProvider;
    private ArtistReleasesTransformer artistReleasesTransformer;

    public ArtistReleasesPresenter(ArtistReleasesContract.View view, DiscogsInteractor discogsInteractor, ArtistReleasesController controller,
                                   BehaviorRelay<List<ArtistRelease>> behaviorRelay,
                                   MySchedulerProvider mySchedulerProvider, ArtistReleasesTransformer artistReleasesTransformer)
    {
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.controller = controller;
        this.behaviorRelay = behaviorRelay;
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistReleasesTransformer = artistReleasesTransformer;
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
    public void setupFilter()
    {
        view.filterIntent().subscribe(filterText ->
        {
            artistReleasesTransformer.setFilterText(filterText);
            if (behaviorRelay.getValue() != null && behaviorRelay.getValue().size() != 0)
                behaviorRelay.accept(behaviorRelay.getValue());
        });
    }
}
