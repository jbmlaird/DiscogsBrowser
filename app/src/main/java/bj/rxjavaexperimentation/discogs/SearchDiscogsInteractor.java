package bj.rxjavaexperimentation.discogs;

import android.content.Context;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import bj.rxjavaexperimentation.model.artistrelease.RootArtistReleaseResponse;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.master.Master;
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
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

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
        return discogsService.getRelease(id, token)
                .map(release ->
                {
                    if (release.getLowestPrice() != null && !release.getLowestPrice().equals(""))
                        release.setLowestPriceString(numberFormat.format(release.getLowestPrice()));
                    return release;
                });
    }

    public Observable<Master> fetchMasterDetails(String masterId)
    {
        return discogsService.getMaster(masterId, token)
                .map(master ->
                {
                    if (master.getLowestPrice() != null && !master.getLowestPrice().equals(""))
                        master.setLowestPriceString(numberFormat.format(master.getLowestPrice()));
                    return master;
                });
    }

    public Observable<Label> fetchLabelDetails(String labelId)
    {
        return discogsService.getLabel(labelId, token);
    }

    public void getArtistsReleases(String artistId, BehaviorRelay<List<ArtistRelease>> behaviorRelay)
    {
        discogsService.getArtistReleases(artistId, token, "desc", "500")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMapIterable(RootArtistReleaseResponse::getArtistReleases)
                .filter(release -> (!release.getRole().equals("TrackAppearance") && !release.getRole().equals("Appearance")))
                .toList()
                .subscribe(behaviorRelay);
    }


}
