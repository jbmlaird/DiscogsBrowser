package bj.rxjavaexperimentation.release;

import bj.rxjavaexperimentation.common.RecyclerViewPresenter;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;

/**
 * Created by Josh Laird on 23/04/2017.
 */

public interface ReleaseContract
{
    interface View
    {
        void displayListingInformation(String title, String subtitle, ScrapeListing scrapeListing);
    }

    interface Presenter extends RecyclerViewPresenter {}
}
