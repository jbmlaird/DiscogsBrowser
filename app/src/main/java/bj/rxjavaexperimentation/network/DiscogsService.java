package bj.rxjavaexperimentation.network;

import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artistrelease.RootArtistReleaseResponse;
import bj.rxjavaexperimentation.model.collection.AddToCollectionResponse;
import bj.rxjavaexperimentation.model.collection.RootCollectionRelease;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.RootLabelResponse;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.listing.RootListingResponse;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.model.order.RootOrderResponse;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import bj.rxjavaexperimentation.model.user.User;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.model.version.RootVersionsResponse;
import bj.rxjavaexperimentation.model.wantlist.AddToWantlistResponse;
import bj.rxjavaexperimentation.model.wantlist.RootWantlistResponse;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interface to interact with the Discogs API.
 */
public interface DiscogsService
{
    @GET("database/search")
    Observable<RootSearchResponse> getSearchResults(@Query("q") String searchTerm);

    @GET("releases/{release_id}")
    Observable<Release> getRelease(@Path("release_id") String releaseId);

    @GET("artists/{artist_id}")
    Observable<ArtistResult> getArtist(@Path("artist_id") String artistId);

    @GET("masters/{master_id}")
    Observable<Master> getMaster(@Path("master_id") String masterId);

    @GET("masters/{master_id}/versions")
    Observable<RootVersionsResponse> getMasterVersions(@Path("master_id") String masterId);

    @GET("labels/{label_id}")
    Observable<Label> getLabel(@Path("label_id") String labelId);

    @GET("labels/{label_id}/releases")
    Observable<RootLabelResponse> getLabelReleases(@Path("label_id") String labelId, @Query("sort_order") String sort, @Query("per_page") String perPage);

    @GET("artists/{artist_id}/releases")
    Observable<RootArtistReleaseResponse> getArtistReleases(@Path("artist_id") String artistId, @Query("sort_order") String sort, @Query("per_page") String perPage);

    @GET("/marketplace/listings/{listing_id}")
    Observable<Listing> getListing(@Path("listing_id") String listingId, @Query("curr_abbr") String currency);

    @GET("/oauth/identity")
    Observable<User> fetchIdentity();

    @GET("/users/{username}")
    Observable<UserDetails> fetchUserDetails(@Path("username") String username);

    // 0 means it will always look in the user's "Uncategorized" folder (must be authenticated)
    @GET("/users/{username}/collection/folders/1/releases")
    Observable<RootCollectionRelease> fetchCollection(@Path("username") String username, @Query("sort") String sortBy, @Query("sort_order") String sortOrder, @Query("per_page") String perPage);

    // 0 means it will always look in the user's "Uncategorized" folder (must be authenticated)
    @POST("/users/{username}/collection/folders/1/releases/{release_id}")
    Observable<AddToCollectionResponse> addToCollection(@Path("username") String username, @Path("release_id") String releaseId);

    @DELETE("/users/{username}/collection/folders/1/releases/{release_id}/instances/{instance_id}")
    Observable<Response<Void>> removeFromCollection(@Path("username") String username, @Path("release_id") String releaseId, @Path("instance_id") String instanceId);

    @PUT("/users/{username}/wants/{release_id}")
    Observable<AddToWantlistResponse> addToWantlist(@Path("username") String username, @Path("release_id") String releaseId);

    @DELETE("/users/{username}/wants/{release_id}")
    Observable<Response<Void>> removeFromWantlist(@Path("username") String username, @Path("release_id") String releaseId);

    @GET("/users/{username}/wants")
    Observable<RootWantlistResponse> fetchWantlist(@Path("username") String username, @Query("sort") String sortBy, @Query("sort_order") String sortOrder, @Query("per_page") String perPage);

    @GET("/marketplace/orders")
    Observable<RootOrderResponse> fetchOrders(@Query("sort_order") String sortOrder, @Query("per_page") String perPage, @Query("sort") String sortBy);

    @GET("/users/{username}/inventory")
    Observable<RootListingResponse> fetchSelling(@Path("username") String username, @Query("sort_order") String desc, @Query("per_page") String perPage, @Query("sort") String sortBy);

    @GET("/marketplace/orders/{order_id}")
    Observable<Order> fetchOrderDetails(@Path("order_id") String orderId);
}
