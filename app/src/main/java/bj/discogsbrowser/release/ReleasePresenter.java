package bj.discogsbrowser.release;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 * <p>
 * TODO: Refactor? There's a chain of requests here and it seems tightly coupled.
 */
@Singleton
public class ReleasePresenter implements ReleaseContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private final ReleaseController controller;
    private final DiscogsInteractor discogsInteractor;
    private LabelInteractor labelInteractor;
    private CollectionWantlistInteractor collectionWantlistInteractor;
    private final MySchedulerProvider mySchedulerProvider;
    private final ArtistsBeautifier artistsBeautifier;
    private DaoManager daoManager;
    private boolean collectionChecked;
    private boolean wantlistChecked;

    @Inject
    public ReleasePresenter(@NonNull ReleaseController controller, @NonNull DiscogsInteractor discogsInteractor, @NonNull LabelInteractor labelInteractor,
                            @NonNull CollectionWantlistInteractor collectionWantlistInteractor,
                            @NonNull MySchedulerProvider mySchedulerProvider, @NonNull DaoManager daoManager, @NonNull ArtistsBeautifier artistsBeautifier)
    {
        this.controller = controller;
        this.discogsInteractor = discogsInteractor;
        this.labelInteractor = labelInteractor;
        this.collectionWantlistInteractor = collectionWantlistInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.daoManager = daoManager;
        this.artistsBeautifier = artistsBeautifier;
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
    public void getReleaseAndLabelDetails(String id)
    {
        discogsInteractor.fetchReleaseDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .doOnSubscribe(onSubscribe -> controller.setReleaseLoading(true))
                .map(release ->
                {
                    for (Label releaseLabel : release.getLabels())
                    {
                        labelInteractor.fetchLabelDetails(releaseLabel.getId())
                                .subscribe(labelDetails ->
                                        {
                                            if (labelDetails.getImages() != null && labelDetails.getImages().size() > 0)
                                                releaseLabel.setThumb(labelDetails.getImages().get(0).getUri());
                                        },
                                        error ->
                                                Log.e("LabelInteractor", "Unable to get label details") //Silently swallow
                                );
                    }
                    daoManager.storeViewedRelease(release, artistsBeautifier);
                    return release;
                })
                .subscribe(release ->
                {
                    controller.setRelease(release);
                    if (release.getNumForSale() != 0)
                        fetchReleaseListings(id);
                    else
                        controller.setReleaseListings(new ArrayList<>());
                    checkCollectionAndWantlist();
                }, error ->
                        controller.setReleaseError(true));
    }

    public void fetchReleaseListings(String id) throws IOException
    {
        discogsInteractor.getReleaseMarketListings(id, "release")
                .doOnSubscribe(onSubscribe -> controller.setMarketplaceLoading(true))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(controller::setReleaseListings,
                        error ->
                                controller.setReleaseListingsError()
                );
    }

    @Override
    public void checkCollectionAndWantlist()
    {
        collectionWantlistInteractor.checkIfInCollection(controller, controller.getRelease())
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
                        });
        collectionWantlistInteractor.checkIfInWantlist(controller, controller.getRelease())
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
                        });
    }
}