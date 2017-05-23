package bj.vinylbrowser.artistreleases;

import android.support.annotation.VisibleForTesting;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.vinylbrowser.model.artistrelease.ArtistRelease;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;

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
                                   ArtistReleaseBehaviorRelay behaviorRelay, MySchedulerProvider mySchedulerProvider,
                                   ArtistReleasesTransformer artistReleasesTransformer)
    {
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.controller = controller;
        this.behaviorRelay = behaviorRelay.getArtistReleaseBehaviorRelay();
        this.mySchedulerProvider = mySchedulerProvider;
        this.artistReleasesTransformer = artistReleasesTransformer;
    }

    /**
     * Fetch artist releases from Discogs.
     *
     * @param id Artist ID.
     */
    @Override
    public void fetchArtistReleases(String id)
    {
        discogsInteractor.fetchArtistsReleases(id)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(behaviorRelay, error ->
                {
                    error.printStackTrace();
                    controller.setError("Unable to fetch Artist Releases");
                });
    }

    /**
     * Sets up the filter field to automatically emit its value to the {@link ArtistReleasesTransformer}.
     * <p>
     * If the {@link ArtistReleaseBehaviorRelay} has already emitted an item, it re-emits with this filter applied.
     */
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

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setBehaviorRelay(BehaviorRelay<List<ArtistRelease>> artistReleases)
    {
        behaviorRelay = artistReleases;
    }
}
