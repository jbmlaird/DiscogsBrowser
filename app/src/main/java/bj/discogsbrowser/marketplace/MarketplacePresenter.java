package bj.discogsbrowser.marketplace;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 13/04/2017.
 */
public class MarketplacePresenter implements MarketplaceContract.Presenter
{
    private static final String TAG = "MarketplacePresenter";
    private MarketplaceContract.View view;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public MarketplacePresenter(@NonNull MarketplaceContract.View view, @NonNull DiscogsInteractor discogsInteractor, @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    @Override
    public void getListingDetails(String listingId)
    {
        discogsInteractor.fetchListingDetails(listingId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .flatMap(listing ->
                {
                    view.displayListing(listing);
                    return discogsInteractor.fetchUserDetails(listing.getSeller().getUsername());
                })
                .subscribe(userDetails ->
                        view.updateUserDetails(userDetails), Throwable::printStackTrace);
    }
}
