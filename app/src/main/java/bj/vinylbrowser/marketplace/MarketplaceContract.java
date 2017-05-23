package bj.vinylbrowser.marketplace;

import bj.vinylbrowser.common.BaseView;

/**
 * Created by Josh Laird on 13/04/2017.
 */

public interface MarketplaceContract
{
    interface View extends BaseView
    {
        void viewOnDiscogs(String listingUri);

        void viewSellerShipping(String shippingDetails, String username);
    }

    interface Presenter
    {
        void getListingDetails(String listingId);
    }
}
