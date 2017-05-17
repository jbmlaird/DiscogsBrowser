package bj.discogsbrowser.marketplace;

import android.content.Context;
import android.support.annotation.NonNull;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 13/04/2017.
 */
public class MarketplacePresenter implements MarketplaceContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private MarketplaceContract.View view;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private MarketplaceController controller;

    public MarketplacePresenter(@NonNull Context context, @NonNull MarketplaceContract.View view, @NonNull DiscogsInteractor discogsInteractor, @NonNull MySchedulerProvider mySchedulerProvider,
                                @NonNull MarketplaceController controller)
    {
        this.context = context;
        this.view = view;
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
                                controller.setError(true));
    }
}
