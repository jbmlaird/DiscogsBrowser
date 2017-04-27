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
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class ReleasePresenter implements ReleaseContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private final ReleaseContract.View mView;
    private final ReleaseController controller;
    private final CompositeDisposable compositeDisposable;
    private final DiscogsInteractor discogsInteractor;
    private final MySchedulerProvider mySchedulerProvider;
    private SharedPrefsManager sharedPrefsManager;
    private final LogWrapper log;
    private boolean collectionChecked;
    private boolean wantlistChecked;

    @Inject
    public ReleasePresenter(@NonNull ReleaseContract.View view, @NonNull ReleaseController controller, @NonNull CompositeDisposable compositeDisposable, @NonNull DiscogsInteractor discogsInteractor,
                            @NonNull MySchedulerProvider mySchedulerProvider, @NonNull SharedPrefsManager sharedPrefsManager, @NonNull LogWrapper log)
    {
        this.mView = view;
        this.controller = controller;
        this.compositeDisposable = compositeDisposable;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
    }

    @Override
    public void getData(String id)
    {
        compositeDisposable.add(discogsInteractor.fetchReleaseDetails(id)
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
                }));
    }

    private void checkIfInWantlist(Release release)
    {
        discogsInteractor.fetchWantlist(sharedPrefsManager.getUsername())
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .flatMapIterable(results -> results)
                .filter(collectionRelease -> collectionRelease.getId().equals(release.getId()))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(want ->
                        {
                            if (want.getId().equals(release.getId()))
                                release.setIsInWantlist(true);
                        },
                        error ->
                        {
                            error.printStackTrace();
                            Log.e("wantlist", "wtf");
                        },
                        () ->
                        {
                            // onComplete()
                            wantlistChecked = true;
                            if (collectionChecked)
                                controller.collectionWantlistChecked(true);
                        });
    }

    private void checkIfInCollection(Release release)
    {
        discogsInteractor.fetchCollection(sharedPrefsManager.getUsername())
                .observeOn(mySchedulerProvider.io())
                .subscribeOn(mySchedulerProvider.io())
                .flatMapIterable(results -> results)
                .filter(collectionRelease -> collectionRelease.getId().equals(release.getId()))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(result ->
                        {
                            if (result.getId().equals(release.getId()))
                            {
                                release.setIsInCollection(true);
                                release.setInstanceId(result.getInstanceId());
                            }
                        },
                        error ->
                        {
                            error.printStackTrace();
                            Log.e("collection", "wtf");
                        },
                        () ->
                        {
                            // onComplete()
                            collectionChecked = true;
                            if (wantlistChecked)
                                controller.collectionWantlistChecked(true);
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
