package bj.rxjavaexperimentation.network;

import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artistrelease.RootArtistReleaseResponse;
import bj.rxjavaexperimentation.model.collectionrelease.RootCollectionRelease;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.RootLabelResponse;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import bj.rxjavaexperimentation.model.user.User;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.model.wantlist.RootWantlistResponse;
import io.reactivex.Observable;
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
    @GET("database/search?")
    Observable<RootSearchResponse> getSearchResults(@Query("q") String searchTerm);

    @GET("releases/{release_id}")
    Observable<Release> getRelease(@Path("release_id") String releaseId);

    @GET("artists/{artist_id}")
    Observable<ArtistResult> getArtist(@Path("artist_id") String artistId);

    @GET("masters/{master_id}")
    Observable<Master> getMaster(@Path("master_id") String masterId);

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

    // 0 means it will always look in the user's "All" folder
    @GET("/users/{username}/collection/folders/0/releases")
    Observable<RootCollectionRelease> fetchCollection(@Path("username") String username, @Query("sort_order") String sort, @Query("per_page") String perPage);

    @GET("/users/{username}/wants")
    Observable<RootWantlistResponse> fetchWantlist(@Path("username") String username, @Query("sort_order") String sort, @Query("per_page") String perPage);
}
