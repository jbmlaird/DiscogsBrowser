package bj.rxjavaexperimentation.discogs;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import bj.rxjavaexperimentation.model.search.SearchResult;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interactor to interact with the Discogs API.
 */
@Singleton
public class SearchDiscogsInteractor
{
    private String token;
    private DiscogsService discogsService;
    private Context mContext;

    @Inject
    public SearchDiscogsInteractor(Context context, Retrofit retrofit)
    {
        mContext = context;
        token = mContext.getString(R.string.token);
        discogsService = retrofit.create(DiscogsService.class);
    }

    public Observable<List<SearchResult>> searchDiscogs(String searchTerm)
    {
        return discogsService.getSearchResults(searchTerm, token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(RootSearchResponse::getSearchResults);
    }

    public Observable<ArtistResult> fetchArtistDetails(String id)
    {
        return discogsService.getArtist(id, token);
    }

    public Observable<Release> fetchReleaseDetails(String id)
    {
        return discogsService.getRelease(id, token);
    }

//    public Single<List<Release>> getArtistsReleases(String artistId)
//    {
//        return discogsService.getArtistReleases(artistId, token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .flatMapIterable(RootReleaseResponse::getReleases)
//                .take(4)
//                .flatMap(searchResult ->
//                        discogsService.getRelease(searchResult.getId(), token))
//                .toList();
//    }
}
