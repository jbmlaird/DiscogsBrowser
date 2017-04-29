package bj.rxjavaexperimentation.release;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.wrappers.LogWrapper;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class ReleasePresenter implements ReleaseContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private final ReleaseContract.View mView;
    private final ReleaseController controller;
    private final DiscogsInteractor discogsInteractor;
    private final MySchedulerProvider mySchedulerProvider;
    private SharedPrefsManager sharedPrefsManager;
    private final LogWrapper log;
    private boolean collectionChecked;
    private boolean wantlistChecked;

    @Inject
    public ReleasePresenter(@NonNull ReleaseContract.View view, @NonNull ReleaseController controller, @NonNull DiscogsInteractor discogsInteractor,
                            @NonNull MySchedulerProvider mySchedulerProvider, @NonNull SharedPrefsManager sharedPrefsManager, @NonNull LogWrapper log)
    {
        this.mView = view;
        this.controller = controller;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
    }

    @Override
    public void getData(String id)
    {
        discogsInteractor.fetchReleaseDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(release ->
                {
                    controller.setRelease(release);
                    log.e(TAG, release.getTitle());
                    if (release.getNumForSale() != 0)
                        discogsInteractor.getReleaseMarketListings(id, "release")
                                .subscribeOn(mySchedulerProvider.io())
                                .observeOn(mySchedulerProvider.ui())
                                .subscribe(controller::setReleaseListings,
                                        error ->
                                                controller.setReleaseListingsError()
                                );
                    else
                        controller.setReleaseListings(new ArrayList<>());

                    checkIfInCollection(release);
                    checkIfInWantlist(release);
                }, error ->
                {
                    log.e(TAG, "onReleaseDetailsError");
                    error.printStackTrace();
                });
    }

    private void checkIfInWantlist(Release release)
    {
        discogsInteractor.fetchWantlist(sharedPrefsManager.getUsername())
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .flattenAsObservable(results -> results)
                .map(want ->
                {
                    if (want.getId().equals(release.getId()))
                        release.setIsInWantlist(true);
                    return want;
                })
                .observeOn(mySchedulerProvider.ui())
                .subscribe(want ->
                        {
                            wantlistChecked = true;
                            if (collectionChecked)
                                controller.collectionWantlistChecked(true);
                        },
                        error ->
                        {
                            controller.setWantlistError(true);
                            error.printStackTrace();
                            Log.e(TAG, "checkInWantlistFail");
                        });
    }

    private void checkIfInCollection(Release release)
    {
        discogsInteractor.fetchCollection(sharedPrefsManager.getUsername())
                .observeOn(mySchedulerProvider.io())
                .subscribeOn(mySchedulerProvider.io())
                .flattenAsObservable(results -> results)
                .map(collectionRelease ->
                {
                    if (collectionRelease.getId().equals(release.getId()))
                    {
                        release.setIsInCollection(true);
                        release.setInstanceId(collectionRelease.getInstanceId());
                    }
                    return collectionRelease;
                })
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            collectionChecked = true;
                            if (wantlistChecked)
                                controller.collectionWantlistChecked(true);
                        },
                        error ->
                        {
                            error.printStackTrace();
                            controller.setCollectionError(true);
                            Log.e(TAG, "checkInCollection");
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
