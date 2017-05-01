package bj.rxjavaexperimentation.singlelist;

import android.support.v7.widget.RecyclerView;

import bj.rxjavaexperimentation.common.BasePresenter;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public interface SingleListContract
{
    interface View
    {
        Observable<CharSequence> filterIntent();

        void stopLoading();

        void showNoItems(boolean showNoItems, String s);

        void showError(boolean showError, String s);

        void displayListing(String listingId, String title, String subtitle, String seller);

        void displayOrder(String id);

        void launchDetailedActivity(String type, String title, String id);
    }

    interface Presenter extends BasePresenter
    {
        void getData(String type, String username);

        void setupRecyclerView(SingleListActivity singleListActivity, RecyclerView recyclerView);

        void setupFilterSubscription();
    }
}
