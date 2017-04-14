package bj.rxjavaexperimentation.network;

import android.content.Context;
import android.util.Log;

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
import io.reactivex.Single;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interactor to interact with the Discogs API.
 */
@Singleton
public class SearchDiscogsInteractor
{
    private final CacheProviders cacheProviders;
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

        cacheProviders = new RxCache.Builder()
                .persistence(context.getFilesDir(), new GsonSpeaker())
                .using(CacheProviders.class);
    }

    public Observable<List<SearchResult>> searchDiscogs(String searchTerm)
    {
        return cacheProviders.searchDiscogs(discogsService.getSearchResults(searchTerm, token), new DynamicKey(searchTerm))
                .map(RootSearchResponse::getSearchResults);
    }

    public Observable<ArtistResult> fetchArtistDetails(String artistId)
    {
        return cacheProviders.fetchArtistDetails(discogsService.getArtist(artistId, token), new DynamicKey(artistId));
    }

    public Observable<Release> fetchReleaseDetails(String releaseId)
    {
        return cacheProviders.fetchReleaseDetails(discogsService.getRelease(releaseId, token), new DynamicKey(releaseId))
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
        return cacheProviders.fetchMasterDetails(discogsService.getMaster(masterId, token), new DynamicKey(masterId))
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
        return cacheProviders.fetchLabelDetails(discogsService.getLabel(labelId, token), new DynamicKey(labelId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io());
    }

    public Observable<List<LabelRelease>> fetchLabelReleases(String labelId)
    {
        return cacheProviders.fetchLabelReleases(discogsService.getLabelReleases(labelId, token, "desc", "500"), new DynamicKey(labelId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(RootLabelResponse::getLabelReleases);
    }

    public Single<List<ArtistRelease>> fetchArtistsReleases(String artistId)
    {
        return cacheProviders.fetchArtistsReleases(discogsService.getArtistReleases(artistId, token, "desc", "500"), new DynamicKey(artistId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .flatMapIterable(RootArtistReleaseResponse::getArtistReleases)
                .filter(release -> (!release.getRole().equals("TrackAppearance") && !release.getRole().equals("Appearance")))
                .toList()
                .doOnError(throwable ->
                        Log.e("SearchDiscogsInteractor", "fetchArtistsReleases error")
                );
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
        return cacheProviders.getReleaseMarketListings(Observable.create(emitter ->
                emitter.onNext(discogsScraper.scrapeListings(id, type))), new DynamicKey(id + type));
    }

    public Observable<Listing> fetchListingDetails(String listingId)
    {
        return cacheProviders.fetchListingDetails(discogsService.getListing(listingId, token, "GBP"), new DynamicKey(listingId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io());
    }
}
