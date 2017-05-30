package bj.vinylbrowser.master;

import android.support.annotation.NonNull;

import bj.vinylbrowser.model.common.Label;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class MasterPresenter implements MasterContract.Presenter
{
    private DiscogsInteractor discogsInteractor;
    private MasterEpxController controller;
    private MySchedulerProvider mySchedulerProvider;

    public MasterPresenter(@NonNull DiscogsInteractor discogsInteractor, @NonNull MasterEpxController masterController, @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.discogsInteractor = discogsInteractor;
        this.controller = masterController;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    /**
     * Fetches {@link Label} details from Discogs.
     *
     * @param labelId Label ID.
     */
    @Override
    public void fetchArtistDetails(String labelId)
    {
        discogsInteractor.fetchMasterDetails(labelId)
                .doOnSubscribe(onSubscribe -> controller.setLoading(true))
                .observeOn(mySchedulerProvider.ui())
                .flatMap(master ->
                {
                    controller.setMaster(master);
                    return discogsInteractor.fetchMasterVersions(master.getId());
                })
                .subscribe(masterVersions ->
                                controller.setMasterMasterVersions(masterVersions),
                        error ->
                        {
                            controller.setError(true);
                            error.printStackTrace();
                        });
    }
}
