package bj.rxjavaexperimentation.discogs;

import bj.rxjavaexperimentation.gson.RootResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interface to interact with the Discogs API.
 */
public interface DiscogsService
{
    @GET("database/search?")
    Observable<RootResponse> getSearchResults(@Query("q") String searchTerm, @Query("token") String token);
}
