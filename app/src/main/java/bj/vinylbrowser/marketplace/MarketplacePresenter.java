package bj.vinylbrowser.marketplace;

import android.support.annotation.NonNull;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 13/04/2017.
 */
public class MarketplacePresenter implements MarketplaceContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private MarketplaceEpxController controller;

    public MarketplacePresenter(@NonNull DiscogsInteractor discogsInteractor, @NonNull MySchedulerProvider mySchedulerProvider,
                                @NonNull MarketplaceEpxController controller)
    {
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.controller = controller;
    }

    /**
     * Fetches listing information from Discogs.
     *
     * @param listingId Listing ID.
     */
    @Override
    public void getListingDetails(String listingId)
    {
        discogsInteractor.fetchListingDetails(listingId)
                .doOnSubscribe(onSubscribe -> controller.setLoading(true))
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .flatMap(listing ->
                {
                    controller.setListing(listing);
                    return discogsInteractor.fetchUserDetails(listing.getSeller().getUsername())
                            .observeOn(mySchedulerProvider.ui());
                })
                .subscribe(userDetails ->
                                controller.setSellerDetails(userDetails),
                        error ->
                        {
                            error.printStackTrace();
                            controller.setError(true);
                        });
    }
}
