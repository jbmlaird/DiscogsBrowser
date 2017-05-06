package bj.discogsbrowser.release;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.greendao.DaoSession;
import bj.discogsbrowser.greendao.ViewedRelease;
import bj.discogsbrowser.greendao.ViewedReleaseDao;
import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;

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
    private ViewedReleaseDao viewedReleaseDao;
    private ArtistsBeautifier artistsBeautifier;
    private boolean collectionChecked;
    private boolean wantlistChecked;

    @Inject
    public ReleasePresenter(@NonNull ReleaseContract.View view, @NonNull ReleaseController controller, @NonNull DiscogsInteractor discogsInteractor,
                            @NonNull MySchedulerProvider mySchedulerProvider, @NonNull SharedPrefsManager sharedPrefsManager, @NonNull LogWrapper log,
                            @NonNull DaoSession daoSession, @NonNull ArtistsBeautifier artistsBeautifier)
    {
        this.mView = view;
        this.controller = controller;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
        this.viewedReleaseDao = daoSession.getViewedReleaseDao();
        this.artistsBeautifier = artistsBeautifier;
    }

    @Override
    public void getData(String id)
    {
        discogsInteractor.fetchReleaseDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .doOnSubscribe(onSubscribe -> controller.setReleaseLoading(true))
                .map(release ->
                {
                    for (Label label : release.getLabels())
                    {
                        discogsInteractor.fetchLabelDetails(label.getId())
                                .subscribe(labelDetails ->
                                        {
                                            if (labelDetails.getImages() != null && labelDetails.getImages().size() > 0)
                                                label.setThumb(labelDetails.getImages().get(0).getUri());
                                        },
                                        error ->
                                                Log.e(TAG, "Unable to get label details"));
                    }
                    saveReleaseToDao(release);
                    return release;
                })
                .subscribe(release ->
                {
                    controller.setRelease(release);
                    log.e(TAG, release.getTitle());
                    if (release.getNumForSale() != 0)
                        fetchReleaseListings(id);
                    else
                        controller.setReleaseListings(new ArrayList<>());

                    checkIfInCollection(release);
                    checkIfInWantlist(release);
                }, error ->
                {
                    log.e(TAG, "onReleaseDetailsError");
                    error.printStackTrace();
                    controller.setReleaseError(true);
                });
    }

    private void saveReleaseToDao(Release release)
    {
        ViewedRelease viewedRelease = new ViewedRelease();
        if (release.getStyles() != null && release.getGenres().size() > 0)
            viewedRelease.setStyle(TextUtils.join(",", release.getStyles()));
        viewedRelease.setReleaseId(release.getId());
        if (release.getImages() != null && release.getImages().size() > 0)
            viewedRelease.setThumbUrl(release.getImages().get(0).getResourceUrl());
        else
            viewedRelease.setThumbUrl(release.getThumb());
        viewedRelease.setDate(new Date());
        viewedRelease.setReleaseName(release.getTitle());
        if (release.getLabels() != null && release.getLabels().size() > 0)
            viewedRelease.setLabelName(release.getLabels().get(0).getName());
        if (release.getArtists() != null && release.getArtists().size() > 0)
            viewedRelease.setArtists(artistsBeautifier.formatArtists(release.getArtists()));
        viewedReleaseDao.insertOrReplace(viewedRelease);
    }

    public void fetchReleaseListings(String id) throws IOException
    {
        discogsInteractor.getReleaseMarketListings(id, "release")
                .doOnSubscribe(onSubscribe -> controller.setMarketplaceLoading(true))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(controller::setReleaseListings,
                        error ->
                                controller.setReleaseListingsError()
                );
    }

    private void checkIfInWantlist(Release release)
    {
        discogsInteractor.fetchWantlist(sharedPrefsManager.getUsername())
                .doOnSubscribe(onSubscribe -> controller.setCollectionLoading(true))
                .subscribeOn(mySchedulerProvider.ui())
                .doOnSuccess(onSuccess ->
                {
                    wantlistChecked = true;
                    if (collectionChecked)
                        controller.collectionWantlistChecked(true);
                })
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
                            // Due to the filter, if the user has nothing in their Collection/Wantlist, this will not be reached.
                            // Done in onSuccess() instead
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
                .doOnSubscribe(onSubscribe -> controller.setCollectionLoading(true))
                .doOnSuccess(onSuccess ->
                {
                    collectionChecked = true;
                    if (wantlistChecked)
                        controller.collectionWantlistChecked(true);
                })
                .subscribeOn(mySchedulerProvider.ui())
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
                            // Due to the filter, if the user has nothing in their Collection/Wantlist, this will not be reached.
                            // Done in onSuccess() instead
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

    @Override
    public void retryCollectionWantlist()
    {
        checkIfInCollection(controller.getRelease());
        checkIfInWantlist(controller.getRelease());
    }
}
