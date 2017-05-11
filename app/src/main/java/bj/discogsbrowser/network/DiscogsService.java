package bj.discogsbrowser.network;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artistrelease.RootArtistReleaseResponse;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.listing.RootListingResponse;
import bj.discogsbrowser.model.master.Master;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.order.RootOrderResponse;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.model.search.RootSearchResponse;
import bj.discogsbrowser.model.user.User;
import bj.discogsbrowser.model.user.UserDetails;
import bj.discogsbrowser.model.version.RootVersionsResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
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
    Single<RootSearchResponse> getSearchResults(@Query("q") String searchTerm, @Query("per_page") String perPage);

    @GET("database/search")
    Single<RootSearchResponse> searchByStyle(@Query("style") String style, @Query("type") String type, @Query("per_page") String perPage, @Query("page") String page);

    @GET("database/search")
    Single<RootSearchResponse> searchByLabel(@Query("label") String label, @Query("type") String type, @Query("per_page") String perPage);

    @GET("releases/{release_id}")
    Single<Release> getRelease(@Path("release_id") String releaseId);

    @GET("artists/{artist_id}")
    Single<ArtistResult> getArtist(@Path("artist_id") String artistId);

    @GET("masters/{master_id}")
    Single<Master> getMaster(@Path("master_id") String masterId);

    @GET("masters/{master_id}/versions")
    Single<RootVersionsResponse> getMasterVersions(@Path("master_id") String masterId);

    @GET("artists/{artist_id}/releases")
    Single<RootArtistReleaseResponse> getArtistReleases(@Path("artist_id") String artistId, @Query("sort_order") String sort, @Query("per_page") String perPage);

    @GET("/marketplace/listings/{listing_id}")
    Single<Listing> getListing(@Path("listing_id") String listingId, @Query("curr_abbr") String currency);

    @GET("/oauth/identity")
    Single<User> fetchIdentity();

    @GET("/users/{username}")
    Single<UserDetails> fetchUserDetails(@Path("username") String username);

    @GET("/marketplace/orders")
    Single<RootOrderResponse> fetchOrders(@Query("sort_order") String sortOrder, @Query("per_page") String perPage, @Query("sort") String sortBy);

    @GET("/users/{username}/inventory")
    Single<RootListingResponse> fetchSelling(@Path("username") String username, @Query("sort_order") String desc, @Query("per_page") String perPage, @Query("sort") String sortBy);

    @GET("/marketplace/orders/{order_id}")
    Single<Order> fetchOrderDetails(@Path("order_id") String orderId);

    // Only viewable if you're the seller
//    @GET("/marketplace/orders/{order_id}/messages/")
//    Single<RootOrderMessagesResponse> fetchOrderMessages(@Path("order_id") String orderId);
}
