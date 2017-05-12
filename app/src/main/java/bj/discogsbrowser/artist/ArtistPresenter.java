package bj.discogsbrowser.artist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.rxmodifiers.RemoveUnwantedLinksFunction;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@ActivityScope
public class ArtistPresenter implements ArtistContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private ArtistContract.View view;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private LogWrapper log;
    private ArtistController artistController;
    private RemoveUnwantedLinksFunction removeUnwantedLinksFunction;

    @Inject
    public ArtistPresenter(@NonNull ArtistContract.View view, @NonNull DiscogsInteractor discogsInteractor,
                           @NonNull MySchedulerProvider mySchedulerProvider, @NonNull LogWrapper log, @NonNull ArtistController artistController,
                           @NonNull RemoveUnwantedLinksFunction removeUnwantedLinksFunction)
    {
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.log = log;
        this.artistController = artistController;
        this.removeUnwantedLinksFunction = removeUnwantedLinksFunction;
    }

    @Override
    public void getReleaseAndLabelDetails(String id)
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

    @Override
    public void setupRecyclerView(Context context, RecyclerView rvDetailed, String title)
    {
        rvDetailed.setLayoutManager(new LinearLayoutManager(context));
        rvDetailed.setAdapter(artistController.getAdapter());
        artistController.setTitle(title);
        artistController.requestModelBuild();
    }
}
