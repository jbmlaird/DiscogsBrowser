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
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.model.order.RootOrderResponse;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import bj.rxjavaexperimentation.model.user.User;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.model.wantlist.RootWantlistResponse;
import io.reactivex.Observable;
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
    Observable<RootSearchResponse> searchDiscogs(Observable<RootSearchResponse> searchDiscogsObservable, DynamicKey searchTerm);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<ArtistResult> fetchArtistDetails(Observable<ArtistResult> fetchArtistDetailsObservable, DynamicKey artistId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Release> fetchReleaseDetails(Observable<Release> fetchReleaseDetailsObservable, DynamicKey releaseId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Master> fetchMasterDetails(Observable<Master> fetchMasterDetailsObservable, DynamicKey masterId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Label> fetchLabelDetails(Observable<Label> fetchLabelDetailsObservable, DynamicKey labelId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<RootLabelResponse> fetchLabelReleases(Observable<RootLabelResponse> fetchLabelReleasesObservable, DynamicKey labelId);

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<RootArtistReleaseResponse> fetchArtistsReleases(Observable<RootArtistReleaseResponse> fetchArtistsReleasesSingle, DynamicKey artistId);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<ArrayList<ScrapeListing>> getReleaseMarketListings(Observable<ArrayList<ScrapeListing>> releaseMarketListings, DynamicKey listingIdAndType);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Listing> fetchListingDetails(Observable<Listing> fetchListingDetailsObservable, DynamicKey listingId);

    @LifeCache(duration = 365, timeUnit = TimeUnit.DAYS)
    Observable<User> fetchIdentity(Observable<User> identityObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<UserDetails> fetchUserDetails(Observable<UserDetails> userDetailsObservable, DynamicKey username, EvictDynamicKey update);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<RootCollectionRelease> fetchCollection(Observable<RootCollectionRelease> fetchCollectionObservable, DynamicKey username, EvictDynamicKey update);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<RootWantlistResponse> fetchWantlist(Observable<RootWantlistResponse> fetchWantlistObservable, DynamicKey username, EvictDynamicKey update);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<RootOrderResponse> fetchOrders(Observable<RootOrderResponse> fetchOrdersObservable, DynamicKey username);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<RootListingResponse> fetchSelling(Observable<RootListingResponse> fetchSellingObservable, DynamicKey username);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<Order> fetchOrderDetails(Observable<Order> fetchOrderDetailsObservable, DynamicKey orderId);
}
