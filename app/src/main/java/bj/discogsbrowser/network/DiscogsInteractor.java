package bj.discogsbrowser.network;

import android.content.Context;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.model.artistrelease.RootArtistReleaseResponse;
import bj.discogsbrowser.model.collection.CollectionRelease;
import bj.discogsbrowser.model.collection.RootCollectionRelease;
import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.LabelRelease;
import bj.discogsbrowser.model.labelrelease.RootLabelResponse;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.listing.RootListingResponse;
import bj.discogsbrowser.model.listing.ScrapeListing;
import bj.discogsbrowser.model.master.Master;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.order.RootOrderResponse;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.model.search.RootSearchResponse;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.model.user.UserDetails;
import bj.discogsbrowser.model.version.RootVersionsResponse;
import bj.discogsbrowser.model.version.Version;
import bj.discogsbrowser.model.wantlist.RootWantlistResponse;
import bj.discogsbrowser.model.wantlist.Want;
import bj.discogsbrowser.utils.DiscogsScraper;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interactor to interact with the Discogs API.
 */
@Singleton
public class DiscogsInteractor
{
    private final CacheProviders cacheProviders;
    private SharedPrefsManager sharedPrefsManager;
    private DiscogsService discogsService;
    private MySchedulerProvider mySchedulerProvider;
    private DiscogsScraper discogsScraper;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    @Inject
    public DiscogsInteractor(Context context, Retrofit retrofit, MySchedulerProvider mySchedulerProvider, DiscogsScraper discogsScraper, SharedPrefsManager sharedPrefsManager)
    {
        this.mySchedulerProvider = mySchedulerProvider;
        discogsService = retrofit.create(DiscogsService.class);
        this.discogsScraper = discogsScraper;

        cacheProviders = new RxCache.Builder()
                .setMaxMBPersistenceCache(5)
                .persistence(context.getFilesDir(), new GsonSpeaker())
                .using(CacheProviders.class);
        this.sharedPrefsManager = sharedPrefsManager;
    }

    public Single<List<SearchResult>> searchDiscogs(String searchTerm)
    {
        return cacheProviders.searchDiscogs(discogsService.getSearchResults(searchTerm, "100"), new DynamicKey(searchTerm))
                .map(RootSearchResponse::getSearchResults);
    }

    // Another method for this as I want caching to be separate to the above
    public Single<RootSearchResponse> searchByStyle(String style, String page, boolean update)
    {
        return cacheProviders.searchByStyle(discogsService.searchByStyle(style, "release", "100", page), new DynamicKey(style + page), new EvictDynamicKey(update));
    }

    // Another method for this as I want caching to be separate to the above
    public Single<List<SearchResult>> searchByLabel(String label)
    {
        return cacheProviders.searchByLabel(discogsService.searchByLabel(label, "release", "100"), new DynamicKey(label))
                .map(RootSearchResponse::getSearchResults);
    }

    public Single<ArtistResult> fetchArtistDetails(String artistId)
    {
        return cacheProviders.fetchArtistDetails(discogsService.getArtist(artistId), new DynamicKey(artistId))
                .subscribeOn(mySchedulerProvider.io())
                .map(artistResult ->
                {
                    if (artistResult.getProfile() != null)
                    {
                        artistResult.setProfile(artistResult.getProfile().replace("[a=", ""));
                        artistResult.setProfile(artistResult.getProfile().replace("[i]", ""));
                        artistResult.setProfile(artistResult.getProfile().replace("[/l]", ""));
                        artistResult.setProfile(artistResult.getProfile().replace("[/I]", ""));
                        artistResult.setProfile(artistResult.getProfile().replace("[/i]", ""));
                    }
                    return artistResult;
                });
    }

    public Single<Release> fetchReleaseDetails(String releaseId)
    {
        return cacheProviders.fetchReleaseDetails(discogsService.getRelease(releaseId), new DynamicKey(releaseId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(release ->
                {
                    if (release.getLowestPrice() != null)
                        release.setLowestPriceString(numberFormat.format(release.getLowestPrice()));
                    return release;
                });
    }

    public Single<Master> fetchMasterDetails(String masterId)
    {
        return cacheProviders.fetchMasterDetails(discogsService.getMaster(masterId), new DynamicKey(masterId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(master ->
                {
                    if (master.getLowestPrice() != null)
                        master.setLowestPriceString(numberFormat.format(master.getLowestPrice()));
                    return master;
                });
    }

    public Single<List<Version>> fetchMasterVersions(String masterId)
    {
        return discogsService.getMasterVersions(masterId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io())
                .map(RootVersionsResponse::getVersions);
    }

    public Single<Label> fetchLabelDetails(String labelId)
    {
        return cacheProviders.fetchLabelDetails(discogsService.getLabel(labelId), new DynamicKey(labelId))
                .subscribeOn(mySchedulerProvider.io())
                .map(label ->
                {
                    if (label.getProfile() != null)
                    {
                        label.setProfile(label.getProfile().replace("[a=", ""));
                        label.setProfile(label.getProfile().replace("[i]", ""));
                        label.setProfile(label.getProfile().replace("[/l]", ""));
                        label.setProfile(label.getProfile().replace("[/I]", ""));
                        label.setProfile(label.getProfile().replace("]", ""));
                    }
                    return label;
                });
    }

    public Single<List<LabelRelease>> fetchLabelReleases(String labelId)
    {
        return cacheProviders.fetchLabelReleases(discogsService.getLabelReleases(labelId, "desc", "500"), new DynamicKey(labelId))
                .subscribeOn(mySchedulerProvider.io())
                .map(RootLabelResponse::getLabelReleases);
    }

    public Single<List<ArtistRelease>> fetchArtistsReleases(String artistId)
    {
        return cacheProviders.fetchArtistsReleases(discogsService.getArtistReleases(artistId, "desc", "500"), new DynamicKey(artistId))
                .subscribeOn(mySchedulerProvider.io())
                .flattenAsObservable(RootArtistReleaseResponse::getArtistReleases)
                .filter(release ->
                        (!release.getRole().equals("TrackAppearance") && !release.getRole().equals("Appearance")))
                .toList();
    }

    /**
     * Scrape the market listings as the API endpoint is now private :/
     *
     * @param id   ID of item.
     * @param type Release or master.
     * @return Parsed HTML.
     */
    public Single<ArrayList<ScrapeListing>> getReleaseMarketListings(String id, String type) throws IOException
    {
        return cacheProviders.getReleaseMarketListings(
                Single.defer(() -> Single.just(discogsScraper.scrapeListings(id, type))),
                new DynamicKey(id + type));
    }

    public Single<Listing> fetchListingDetails(String listingId)
    {
        return cacheProviders.fetchListingDetails(discogsService.getListing(listingId, "GBP"), new DynamicKey(listingId))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.io());
    }

    public Single<UserDetails> fetchUserDetails()
    {
        // Assuming that there will be no duplicate OAuth token
        return cacheProviders.fetchIdentity(discogsService.fetchIdentity(), new DynamicKey(sharedPrefsManager.getUserOAuthToken().getToken()))
                .subscribeOn(mySchedulerProvider.io())
                .flatMap(user ->
                        discogsService.fetchUserDetails(user.getUsername()));
    }

    public Single<List<CollectionRelease>> fetchCollection(String username)
    {
        Single<List<CollectionRelease>> collectionSingle = cacheProviders.fetchCollection(discogsService.fetchCollection(username, "year", "desc", "500"), new DynamicKey(username + "desc500"), new EvictDynamicKey(sharedPrefsManager.fetchNextCollection()))
                .subscribeOn(mySchedulerProvider.io())
                .map(RootCollectionRelease::getCollectionReleases);
        if (sharedPrefsManager.fetchNextCollection())
            sharedPrefsManager.setFetchNextCollection("no");
        return collectionSingle;
    }

    public Single<List<Want>> fetchWantlist(String username)
    {
        Single<List<Want>> wantlistSingle = cacheProviders.fetchWantlist(discogsService.fetchWantlist(username, "year", "desc", "500"), new DynamicKey(username + "desc500"), new EvictDynamicKey(sharedPrefsManager.fetchNextWantlist()))
                .observeOn(mySchedulerProvider.io())
                .subscribeOn(mySchedulerProvider.io())
                .map(RootWantlistResponse::getWants);
        if (sharedPrefsManager.fetchNextWantlist())
            sharedPrefsManager.setFetchNextWantlist("no");
        return wantlistSingle;
    }

    public Single<List<Order>> fetchOrders()
    {
        return cacheProviders.fetchOrders(discogsService.fetchOrders("desc", "500", "last_activity"), new DynamicKey(sharedPrefsManager.getUsername()))
                .map(RootOrderResponse::getOrders);
    }

    public Single<Order> fetchOrderDetails(String orderId)
    {
        return cacheProviders.fetchOrderDetails(discogsService.fetchOrderDetails(orderId), new DynamicKey(orderId));
    }

    public Single<List<Listing>> fetchSelling(String username)
    {
        return cacheProviders.fetchSelling(discogsService.fetchSelling(username, "desc", "500", "status"), new DynamicKey(username + "desc500status"))
                .map(RootListingResponse::getListings);
    }

    public Single<UserDetails> fetchUserDetails(String username)
    {
        Single<UserDetails> userDetailsSingle = cacheProviders.fetchUserDetails(discogsService.fetchUserDetails(username), new DynamicKey(username), new EvictDynamicKey(sharedPrefsManager.fetchNextUserDetails()))
                .subscribeOn(mySchedulerProvider.io());
        if (sharedPrefsManager.fetchNextUserDetails())
            sharedPrefsManager.setfetchNextUserDetails("no");
        return userDetailsSingle;
    }
}
