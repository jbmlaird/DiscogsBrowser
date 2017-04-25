package bj.rxjavaexperimentation.master;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class MasterPresenter implements MasterContract.Presenter
{
    private MasterContract.View mView;
    private SearchDiscogsInteractor discogsInteractor;
    private MasterController controller;
    private CompositeDisposable compositeDisposable;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public MasterPresenter(@NonNull MasterContract.View mView, @NonNull SearchDiscogsInteractor discogsInteractor, @NonNull MasterController masterController,
                           @NonNull CompositeDisposable compositeDisposable, @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.mView = mView;
        this.discogsInteractor = discogsInteractor;
        this.controller = masterController;
        this.compositeDisposable = compositeDisposable;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    @Override
    public void getData(String id)
    {
        discogsInteractor.fetchMasterDetails(id)
                .observeOn(mySchedulerProvider.ui())
                .subscribe(master ->
                {
                    controller.setMaster(master);
                    discogsInteractor.fetchMasterVersions(id)
                            .observeOn(mySchedulerProvider.ui())
                            .subscribe(masterVersions ->
                                            controller.setMasterVersions(masterVersions),
                                    error ->
                                    {
                                        controller.setVersionsError(true);
                                        error.printStackTrace();
                                    });
                }, error ->
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
