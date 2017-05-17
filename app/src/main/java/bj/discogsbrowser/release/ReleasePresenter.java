package bj.discogsbrowser.release;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 * <p>
 * TODO: Refactor? There's a chain of requests here and it seems tightly coupled.
 */
public class ReleasePresenter implements ReleaseContract.Presenter
{
    private final ReleaseController controller;
    private final DiscogsInteractor discogsInteractor;
    private final MySchedulerProvider mySchedulerProvider;
    private final ArtistsBeautifier artistsBeautifier;
    private DaoManager daoManager;

    public ReleasePresenter(@NonNull ReleaseController controller, @NonNull DiscogsInteractor discogsInteractor,
                            @NonNull MySchedulerProvider mySchedulerProvider, @NonNull DaoManager daoManager, @NonNull ArtistsBeautifier artistsBeautifier)
    {
        this.controller = controller;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.daoManager = daoManager;
        this.artistsBeautifier = artistsBeautifier;
    }

    /**
     * Fetches release details from Discogs.
     * <p>
     * This fetches the release information, the label (of the release) information, the marketplace
     * listings for the release (if available), whether it's in the user's Wantlist or Collection
     * and displays it to the user.
     *
     * @param releaseId Release ID.
     */
    @Override
    public void fetchReleaseDetails(String releaseId)
    {
        discogsInteractor.fetchReleaseDetails(releaseId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .doOnSubscribe(onSubscribe -> controller.setReleaseLoading(true))
                .map(release ->
                {
                    for (Label releaseLabel : release.getLabels())
                    {
                        discogsInteractor.fetchLabelDetails(releaseLabel.getId())
                                .subscribe(labelDetails ->
                                        {
                                            if (labelDetails.getImages() != null && labelDetails.getImages().size() > 0)
                                                releaseLabel.setThumb(labelDetails.getImages().get(0).getUri());
                                        },
                                        error ->
                                                Log.e("DiscogsInteractor", "Unable to get label details")
                                );
                    }
                    daoManager.storeViewedRelease(release, artistsBeautifier);
                    return release;
                })
                .subscribeOn(mySchedulerProvider.ui())
                .map(release ->
                {
                    controller.setRelease(release);
                    if (release.getNumForSale() != 0)
                        fetchReleaseListings(releaseId);
                    else
                        controller.setReleaseListings(new ArrayList<>());
                    return release;
                })
                .subscribeOn(mySchedulerProvider.io())
                .flatMap(release ->
                        discogsInteractor.checkIfInCollection(controller, controller.getRelease()))
                .flatMap(collectionReleases ->
                        discogsInteractor.checkIfInWantlist(controller, controller.getRelease()))
                .subscribe(release ->
                                controller.setCollectionWantlistChecked(true),
                        error ->
                        {
                            controller.setReleaseError(true);
                            error.printStackTrace();
                        });
    }

    /**
     * Fetches marketplace listings.
     *
     * @param releaseId Release ID.
     * @throws IOException
     */
    public void fetchReleaseListings(String releaseId) throws IOException
    {
        discogsInteractor.getReleaseMarketListings(releaseId)
                .doOnSubscribe(onSubscribe -> controller.setListingsLoading(true))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(controller::setReleaseListings,
                        error ->
                        {
                            error.printStackTrace();
                            controller.setReleaseListingsError();
                        });
    }

    /**
     * Checks whether the release is in the user's Collection or Wantlist.
     */
    @Override
    public void checkCollectionWantlist()
    {
        discogsInteractor.checkIfInCollection(controller, controller.getRelease())
                .flatMap(collectionReleases ->
                        discogsInteractor.checkIfInWantlist(controller, controller.getRelease()))
                .subscribe(wants ->
                                controller.setCollectionWantlistChecked(true),
                        error ->
                                controller.setCollectionWantlistError(true));
    }
}