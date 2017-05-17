package bj.discogsbrowser.master;

import android.support.annotation.NonNull;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class MasterPresenter implements MasterContract.Presenter
{
    private DiscogsInteractor discogsInteractor;
    private MasterController controller;
    private MySchedulerProvider mySchedulerProvider;

    public MasterPresenter(@NonNull DiscogsInteractor discogsInteractor, @NonNull MasterController masterController, @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.discogsInteractor = discogsInteractor;
        this.controller = masterController;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    /**
     * Fetches {@link bj.discogsbrowser.model.collection.Label} details from Discogs.
     *
     * @param labelId Label ID.
     */
    @Override
    public void fetchReleaseDetails(String labelId)
    {
        discogsInteractor.fetchMasterDetails(labelId)
                .doOnSubscribe(onSubscribe -> controller.setLoading(true))
                .observeOn(mySchedulerProvider.ui())
                .flatMap(master ->
                {
                    controller.setMaster(master);
                    return discogsInteractor.fetchMasterVersions(labelId);
                })
                .subscribe(masterVersions ->
                                controller.setMasterVersions(masterVersions),
                        error ->
                        {
                            controller.setError(true);
                            error.printStackTrace();
                        });
    }
}
