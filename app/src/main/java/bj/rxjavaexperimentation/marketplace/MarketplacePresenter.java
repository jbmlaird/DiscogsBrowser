package bj.rxjavaexperimentation.marketplace;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 13/04/2017.
 */
public class MarketplacePresenter implements MarketplaceContract.Presenter
{
    private static final String TAG = "MarketplacePresenter";
    private MarketplaceContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MarketplacePresenter(@NonNull MarketplaceContract.View view, @NonNull SearchDiscogsInteractor searchDiscogsInteractor, @NonNull MySchedulerProvider mySchedulerProvider, @NonNull CompositeDisposable compositeDisposable)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getListingDetails(String listingId)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchListingDetails(listingId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(listing ->
                        {
                            view.displayListing(listing);
                            searchDiscogsInteractor.fetchUserDetails(listing.getSeller().getUsername())
                                    .subscribeOn(mySchedulerProvider.io())
                                    .observeOn(mySchedulerProvider.ui())
                                    .subscribe(userDetails ->
                                            view.updateUserDetails(userDetails));
                        },
                        Throwable::printStackTrace
                ));
    }

    @Override
    public void unsubscribe()
    {
        compositeDisposable.dispose();
    }
}
