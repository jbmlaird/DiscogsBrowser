package bj.rxjavaexperimentation.master;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class MasterPresenter implements MasterContract.Presenter
{
    private MasterContract.View mView;
    private DiscogsInteractor discogsInteractor;
    private MasterController controller;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public MasterPresenter(@NonNull MasterContract.View mView, @NonNull DiscogsInteractor discogsInteractor, @NonNull MasterController masterController,
                           @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.mView = mView;
        this.discogsInteractor = discogsInteractor;
        this.controller = masterController;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    @Override
    public void getData(String id)
    {
        discogsInteractor.fetchMasterDetails(id)
                .observeOn(mySchedulerProvider.ui())
                .flatMap(master ->
                {
                    controller.setMaster(master);
                    return discogsInteractor.fetchMasterVersions(id);
                })
                .subscribe(masterVersions ->
                        {
                            controller.setMasterVersions(masterVersions);
                            controller.setError(false);

                        },
                        error ->
                        {
                            controller.setError(true);
                            error.printStackTrace();
                        });
    }

    @Override
    public void setupRecyclerView(Context context, RecyclerView recyclerView, String title)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(controller.getAdapter());
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
