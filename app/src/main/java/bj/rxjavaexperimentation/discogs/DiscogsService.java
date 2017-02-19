package bj.rxjavaexperimentation.discogs;

import bj.rxjavaexperimentation.discogs.gson.RootSearchResponse;
import bj.rxjavaexperimentation.discogs.gson.release.Release;
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
    Observable<RootSearchResponse> getSearchResults(@Query("q") String searchTerm, @Query("token") String token);

    @GET("releases/{release_id}")
    Observable<Release> getRelease(@Path("release_id") String releaseId, @Query("token") String token);
}
