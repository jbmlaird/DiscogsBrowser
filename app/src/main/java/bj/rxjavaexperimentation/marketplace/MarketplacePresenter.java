package bj.rxjavaexperimentation.marketplace;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import bj.rxjavaexperimentation.discogs.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 13/04/2017.
 */
public class MarketplacePresenter implements MarketplaceContract.Presenter
{
    private static final String TAG = "MarketplacePresenter";
    private MarketplaceContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public MarketplacePresenter(@NonNull MarketplaceContract.View view, @NonNull SearchDiscogsInteractor searchDiscogsInteractor, @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    @Override
    public void getListingDetails(String listingId)
    {
        searchDiscogsInteractor.fetchListingDetails(listingId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(listing ->
                                view.displayListing(listing),
                        error ->
                                Log.e(TAG, "error")
                );
    }
}
