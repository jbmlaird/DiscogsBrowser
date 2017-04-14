package bj.rxjavaexperimentation.network;

import java.util.concurrent.TimeUnit;

import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artistrelease.RootArtistReleaseResponse;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.RootLabelResponse;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by Josh Laird on 14/04/2017.
 * <p>
 * RxCache providers.
 * <p>
 * LifeCache of 1 min for debug purposes.
 */
public interface CacheProviders
{
    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<RootSearchResponse>> searchDiscogs(Observable<RootSearchResponse> searchDiscogsObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<ArtistResult>> fetchArtistDetails(Observable<ArtistResult> fetchArtistDetailsObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<Release>> fetchReleaseDetails(Observable<Release> fetchReleaseDetailsObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<Master>> fetchMasterDetails(Observable<Master> fetchMasterDetailsObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<Label>> fetchLabelDetails(Observable<Label> fetchLabelDetailsObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<RootLabelResponse>> fetchLabelReleases(Observable<RootLabelResponse> fetchLabelReleasesObservable, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<RootArtistReleaseResponse>> fetchArtistsReleases(Observable<RootArtistReleaseResponse> fetchArtistsReleasesSingle, DynamicKey dynamicKey);

    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<Listing>> fetchListingDetails(Observable<Listing> fetchListingDetailsObservable, DynamicKey dynamicKey);
}
