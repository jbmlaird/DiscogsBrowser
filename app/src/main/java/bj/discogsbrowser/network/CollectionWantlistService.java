package bj.discogsbrowser.network;

import bj.discogsbrowser.model.collection.AddToCollectionResponse;
import bj.discogsbrowser.model.collection.RootCollectionRelease;
import bj.discogsbrowser.model.wantlist.AddToWantlistResponse;
import bj.discogsbrowser.model.wantlist.RootWantlistResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Josh Laird on 10/05/2017.
 * <p>
 * Collection and Wantlist edit endpoints.
 */
public interface CollectionWantlistService
{
    // 0 means it will always look in the user's "Uncategorized" folder (must be authenticated)
    @GET("/users/{username}/collection/folders/1/releases")
    Single<RootCollectionRelease> fetchCollection(@Path("username") String username, @Query("sort") String sortBy, @Query("sort_order") String sortOrder, @Query("per_page") String perPage);

    @GET("/users/{username}/wants")
    Single<RootWantlistResponse> fetchWantlist(@Path("username") String username, @Query("sort") String sortBy, @Query("sort_order") String sortOrder, @Query("per_page") String perPage);

    // 0 means it will always look in the user's "Uncategorized" folder (must be authenticated)
    @POST("/users/{username}/collection/folders/1/releases/{release_id}")
    Single<AddToCollectionResponse> addToCollection(@Path("username") String username, @Path("release_id") String releaseId);

    @DELETE("/users/{username}/collection/folders/1/releases/{release_id}/instances/{instance_id}")
    Single<Response<Void>> removeFromCollection(@Path("username") String username, @Path("release_id") String releaseId, @Path("instance_id") String instanceId);

    @PUT("/users/{username}/wants/{release_id}")
    Single<AddToWantlistResponse> addToWantlist(@Path("username") String username, @Path("release_id") String releaseId);

    @DELETE("/users/{username}/wants/{release_id}")
    Single<Response<Void>> removeFromWantlist(@Path("username") String username, @Path("release_id") String releaseId);
}
