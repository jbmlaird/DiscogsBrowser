package bj.vinylbrowser.main;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import java.util.List;

import bj.vinylbrowser.model.listing.Listing;
import bj.vinylbrowser.model.order.Order;
import io.reactivex.Single;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View
    {
        void showLoading(boolean b);

        Activity getActivity();

        void retryHistory();

        void retryRecommendations();

        // RecyclerView gets detached upon adding the NavigationDrawer
        void setupRecyclerView();

        void displayOrder(String id);

        void displayOrdersActivity(String username);

        void displayListingsActivity(String username);

        void displayListing(String listingId, String username, String title, String s, String username1);

        void displayError(boolean b);

        void retry();

        void displayRelease(String releaseName, String id);

        void learnMore();

        void setDrawer(Drawer buildNavigationDrawer);
    }

    interface Presenter
    {
        void connectAndBuildNavigationDrawer(Toolbar toolbar);

        void buildViewedReleases();

        void retry();

        Single<List<Order>> fetchOrders();

        Single<List<Listing>> fetchSelling();

        void buildRecommendations();

        void showLoadingRecommendations(boolean isLoading);
    }
}
