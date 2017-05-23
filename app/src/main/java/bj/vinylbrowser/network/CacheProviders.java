package bj.vinylbrowser.network;

import java.util.List;
import java.util.concurrent.TimeUnit;

import bj.vinylbrowser.model.artist.ArtistResult;
import bj.vinylbrowser.model.artistrelease.RootArtistReleaseResponse;
import bj.vinylbrowser.model.collection.RootCollectionRelease;
import bj.vinylbrowser.model.common.Label;
import bj.vinylbrowser.model.labelrelease.RootLabelResponse;
import bj.vinylbrowser.model.listing.Listing;
import bj.vinylbrowser.model.listing.RootListingResponse;
import bj.vinylbrowser.model.listing.ScrapeListing;
import bj.vinylbrowser.model.master.Master;
import bj.vinylbrowser.model.order.Order;
import bj.vinylbrowser.model.order.RootOrderResponse;
import bj.vinylbrowser.model.release.Release;
import bj.vinylbrowser.model.search.RootSearchResponse;
import bj.vinylbrowser.model.user.User;
import bj.vinylbrowser.model.user.UserDetails;
import bj.vinylbrowser.model.wantlist.RootWantlistResponse;
import io.reactivex.Single;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;

/**
 * Created by Josh Laird on 14/04/2017.
 * <p>
 * RxCache providers.
 * <p>
 * LifeCache of 1 min for debug purposes.
 */
public interface CacheProviders
{
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<RootSearchResponse> searchDiscogs(Single<RootSearchResponse> searchDiscogsSingle, DynamicKey searchTerm);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<RootSearchResponse> searchByStyle(Single<RootSearchResponse> getRecommendationsSingle, DynamicKey styleAndPage, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<RootSearchResponse> searchByLabel(Single<RootSearchResponse> searchByLabelSingle, DynamicKey label);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<ArtistResult> fetchArtistDetails(Single<ArtistResult> fetchArtistDetailsSingle, DynamicKey artistId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<Release> fetchReleaseDetails(Single<Release> fetchReleaseDetailsSingle, DynamicKey releaseId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<Master> fetchMasterDetails(Single<Master> fetchMasterDetailsSingle, DynamicKey masterId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<Label> fetchLabelDetails(Single<Label> fetchLabelDetailsSingle, DynamicKey labelId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<RootLabelResponse> fetchLabelReleases(Single<RootLabelResponse> fetchLabelReleasesSingle, DynamicKey labelId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Single<RootArtistReleaseResponse> fetchArtistsReleases(Single<RootArtistReleaseResponse> fetchArtistsReleasesSingle, DynamicKey artistId);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Single<List<ScrapeListing>> getReleaseMarketListings(Single<List<ScrapeListing>> releaseMarketListings, DynamicKey listingIdAndType);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Single<Listing> fetchListingDetails(Single<Listing> fetchListingDetailsSingle, DynamicKey listingId);

    @LifeCache(duration = 365, timeUnit = TimeUnit.DAYS)
    Single<User> fetchIdentity(Single<User> identitySingle, DynamicKey dynamicKey);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Single<UserDetails> fetchUserDetails(Single<UserDetails> userDetailsSingle, DynamicKey username, EvictDynamicKey update);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Single<RootCollectionRelease> fetchCollection(Single<RootCollectionRelease> fetchCollectionSingle, DynamicKey username, EvictDynamicKey update);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Single<RootWantlistResponse> fetchWantlist(Single<RootWantlistResponse> fetchWantlistSingle, DynamicKey username, EvictDynamicKey update);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Single<RootOrderResponse> fetchOrders(Single<RootOrderResponse> fetchOrdersSingle, DynamicKey username);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Single<RootListingResponse> fetchSelling(Single<RootListingResponse> fetchSellingSingle, DynamicKey username);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Single<Order> fetchOrderDetails(Single<Order> fetchOrderDetailsSingle, DynamicKey orderId);
}
