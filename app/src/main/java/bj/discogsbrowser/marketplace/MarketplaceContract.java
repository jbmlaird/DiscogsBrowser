package bj.discogsbrowser.marketplace;

import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.user.UserDetails;

/**
 * Created by Josh Laird on 13/04/2017.
 */

public interface MarketplaceContract
{
    interface View
    {
        void displayListing(Listing listing);

        void updateUserDetails(UserDetails userDetails);
    }

    interface Presenter
    {
        void getListingDetails(String listingId);
    }
}
