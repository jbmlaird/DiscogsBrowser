package bj.vinylbrowser.artist;

import android.support.annotation.NonNull;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.rxmodifiers.RemoveUnwantedLinksFunction;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.LogWrapper;

/**
 * Created by Josh Laird on 07/04/2017.
 */
public class ArtistPresenter implements ArtistContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private ArtistContract.View view;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private LogWrapper log;
    private ArtistEpxController artistController;
    private RemoveUnwantedLinksFunction removeUnwantedLinksFunction;

    public ArtistPresenter(@NonNull ArtistContract.View view, @NonNull DiscogsInteractor discogsInteractor,
                           @NonNull MySchedulerProvider mySchedulerProvider, @NonNull LogWrapper log, @NonNull ArtistEpxController artistController,
                           @NonNull RemoveUnwantedLinksFunction removeUnwantedLinksFunction)
    {
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.log = log;
        this.artistController = artistController;
        this.removeUnwantedLinksFunction = removeUnwantedLinksFunction;
    }

    /**
     * Fetch the artist's details from Discogs.
     *
     * @param id Artist ID.
     */
    @Override
    public void fetchArtistDetails(String id)
    {
        discogsInteractor.fetchArtistDetails(id)
                .doOnSubscribe(onSubscribe -> artistController.setLoading(true))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(removeUnwantedLinksFunction)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(artist ->
                {
                    artistController.setArtist(artist);
                    log.e(TAG, artist.getProfile());
                }, error ->
                {
                    log.e(TAG, "onFetchArtistDetailsError");
                    error.printStackTrace();
                    artistController.setError(true);
                });
    }
}
