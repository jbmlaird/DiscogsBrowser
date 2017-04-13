package bj.rxjavaexperimentation.marketplace;

import bj.rxjavaexperimentation.model.listing.Listing;

/**
 * Created by Josh Laird on 13/04/2017.
 */

public interface MarketplaceContract
{
    interface View
    {
        void displayListing(Listing listing);
    }

    interface Presenter
    {
        void getListingDetails(String listingId);
    }
}
