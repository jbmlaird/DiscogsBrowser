package bj.rxjavaexperimentation.release;

import bj.rxjavaexperimentation.common.BaseView;
import bj.rxjavaexperimentation.common.RecyclerViewPresenter;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;

/**
 * Created by Josh Laird on 23/04/2017.
 */

public interface ReleaseContract
{
    interface View extends BaseView
    {
        void displayListingInformation(String title, String subtitle, ScrapeListing scrapeListing);

        void launchYouTube(String uri);

        void displayLabel(String title, String id);

        void retryCollectionWantlist();

        void retryListings();
    }

    interface Presenter extends RecyclerViewPresenter
    {
        void retryCollectionWantlist();
    }
}
