package bj.rxjavaexperimentation.discogs;

import android.content.Context;
import android.util.Log;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import bj.rxjavaexperimentation.model.artistrelease.RootArtistReleaseResponse;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;
import bj.rxjavaexperimentation.model.labelrelease.RootLabelResponse;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.listing.MyListing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.utils.DiscogsScraper;
import io.reactivex.Observable;
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
    private MySchedulerProvider mySchedulerProvider;
    private DiscogsScraper discogsScraper;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    @Inject
    public SearchDiscogsInteractor(Context context, Retrofit retrofit, MySchedulerProvider mySchedulerProvider, DiscogsScraper discogsScraper)
    {
        mContext = context;
        this.mySchedulerProvider = mySchedulerProvider;
        token = mContext.getString(R.string.token);
        discogsService = retrofit.create(DiscogsService.class);
        this.discogsScraper = discogsScraper;
    }

    public Observable<List<SearchResult>> searchDiscogs(String searchTerm)
    {
        return discogsService.getSearchResults(searchTerm, token)
                .map(RootSearchResponse::getSearchResults);
    }

    public Observable<ArtistResult> fetchArtistDetails(String id)
    {
        return discogsService.getArtist(id, token);
    }

    public Observable<Release> fetchReleaseDetails(String id)
    {
        return discogsService.getRelease(id, token)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(release ->
                {
                    if (release.getLowestPrice() != null)
                        release.setLowestPriceString(numberFormat.format(release.getLowestPrice()));
                    return release;
                });
    }

    public Observable<Master> fetchMasterDetails(String masterId)
    {
        return discogsService.getMaster(masterId, token)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(master ->
                {
                    if (master.getLowestPrice() != null)
                        master.setLowestPriceString(numberFormat.format(master.getLowestPrice()));
                    return master;
                });
    }

    public Observable<Label> fetchLabelDetails(String labelId)
    {
        return discogsService.getLabel(labelId, token)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io());
    }

    public Observable<List<LabelRelease>> fetchLabelReleases(String labelId)
    {
        return discogsService.getLabelReleases(labelId, token, "desc", "500")
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(RootLabelResponse::getLabelReleases);
    }

    public void fetchArtistsReleases(String artistId, BehaviorRelay<List<ArtistRelease>> behaviorRelay)
    {
        discogsService.getArtistReleases(artistId, token, "desc", "500")
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .flatMapIterable(RootArtistReleaseResponse::getArtistReleases)
                .filter(release -> (!release.getRole().equals("TrackAppearance") && !release.getRole().equals("Appearance")))
                .toList()
                .doOnError(throwable ->
                        Log.e("SearchDiscogsInteractor", "fetchArtistsReleases error")
                )
                .subscribe(behaviorRelay);
    }

    /**
     * Scrape the market listings as the API endpoint is now private :/
     *
     * @param id   ID of item.
     * @param type Release or master.
     * @return Parsed HTML.
     */
    public Observable<ArrayList<MyListing>> getReleaseMarketListings(String id, String type)
    {
        return Observable.create(emitter ->
                emitter.onNext(discogsScraper.scrapeListings(id, type)));
    }

    public Observable<Listing> fetchListingDetails(String listingId)
    {
        return discogsService.getListing(listingId, token, "GBP")
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io());
    }
}
