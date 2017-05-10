package bj.discogsbrowser.network;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.model.collection.AddToCollectionResponse;
import bj.discogsbrowser.model.collection.CollectionRelease;
import bj.discogsbrowser.model.collection.RootCollectionRelease;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.model.wantlist.AddToWantlistResponse;
import bj.discogsbrowser.model.wantlist.RootWantlistResponse;
import bj.discogsbrowser.model.wantlist.Want;
import bj.discogsbrowser.release.ReleaseController;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 10/05/2017.
 */
public class CollectionWantlistInteractor
{
    private Context context;
    private CollectionWantlistService service;
    private SharedPrefsManager sharedPrefsManager;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public CollectionWantlistInteractor(Context context, Retrofit retrofit, SharedPrefsManager sharedPrefsManager, MySchedulerProvider mySchedulerProvider)
    {
        this.context = context;
        this.service = retrofit.create(CollectionWantlistService.class);
        this.sharedPrefsManager = sharedPrefsManager;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    public Single<List<Want>> fetchWantlist(String username)
    {
        return service.fetchWantlist(username, "year", "desc", "500")
                .subscribeOn(mySchedulerProvider.io())
                .map(RootWantlistResponse::getWants);
    }

    public Single<List<CollectionRelease>> fetchCollection(String username)
    {
        return service.fetchCollection(username, "year", "desc", "500")
                .subscribeOn(mySchedulerProvider.io())
                .map(RootCollectionRelease::getCollectionReleases);
    }

    public Single<List<Want>> checkIfInWantlist(ReleaseController controller, Release release)
    {
        return fetchWantlist(sharedPrefsManager.getUsername())
                .doOnSubscribe(onSubscribe -> controller.setCollectionLoading(true))
                .subscribeOn(mySchedulerProvider.ui())
                .flattenAsObservable(results -> results)
                .map(want ->
                {
                    if (want.getId().equals(release.getId()))
                        release.setIsInWantlist(true);
                    return want;
                })
                .toList();
    }

    public Single<List<CollectionRelease>> checkIfInCollection(ReleaseController controller, Release release)
    {
        return fetchCollection(sharedPrefsManager.getUsername())
                .doOnSubscribe(onSubscribe -> controller.setCollectionLoading(true))
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
                .toList();
    }

    public Single<AddToCollectionResponse> addToCollection(String releaseId)
    {
        return service.addToCollection(sharedPrefsManager.getUsername(), releaseId)
                .subscribeOn(mySchedulerProvider.io());
    }

    public Single<Response<Void>> removeFromCollection(String releaseId, String instanceId)
    {
        return service.removeFromCollection(sharedPrefsManager.getUsername(), releaseId, instanceId)
                .subscribeOn(mySchedulerProvider.io());
    }

    public Single<AddToWantlistResponse> addToWantlist(String releaseId)
    {
        return service.addToWantlist(sharedPrefsManager.getUsername(), releaseId)
                .subscribeOn(mySchedulerProvider.io());
    }

    public Single<Response<Void>> removeFromWantlist(String releaseId)
    {
        return service.removeFromWantlist(sharedPrefsManager.getUsername(), releaseId)
                .subscribeOn(mySchedulerProvider.io());
    }
}
