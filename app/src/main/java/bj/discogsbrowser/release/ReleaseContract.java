package bj.discogsbrowser.release;

import bj.discogsbrowser.common.BaseView;
import bj.discogsbrowser.common.RecyclerViewPresenter;
import bj.discogsbrowser.model.listing.ScrapeListing;

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
        void checkCollectionWantlist();
    }
}
