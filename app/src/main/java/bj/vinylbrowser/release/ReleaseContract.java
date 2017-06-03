package bj.vinylbrowser.release;

import bj.vinylbrowser.common.BaseView;
import bj.vinylbrowser.common.RecyclerViewPresenter;
import bj.vinylbrowser.model.listing.ScrapeListing;

/**
 * Created by Josh Laird on 23/04/2017.
 */

public interface ReleaseContract
{
    interface View extends BaseView
    {
        void displayListingInformation(String title, String subtitle, ScrapeListing scrapeListing);

        void displayLabel(String title, String id);

        void retryCollectionWantlist();

        void retryListings();
    }

    interface Presenter extends RecyclerViewPresenter
    {
        void checkCollectionWantlist();
    }
}
