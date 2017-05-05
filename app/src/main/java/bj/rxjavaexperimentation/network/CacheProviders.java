package bj.rxjavaexperimentation.network;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artistrelease.RootArtistReleaseResponse;
import bj.rxjavaexperimentation.model.collection.RootCollectionRelease;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.RootLabelResponse;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.listing.RootListingResponse;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.testmodels.Order;
import bj.rxjavaexperimentation.model.testmodels.RootOrderResponse;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import bj.rxjavaexperimentation.model.user.User;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.model.wantlist.RootWantlistResponse;
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
    Single<ArrayList<ScrapeListing>> getReleaseMarketListings(Single<ArrayList<ScrapeListing>> releaseMarketListings, DynamicKey listingIdAndType);

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
