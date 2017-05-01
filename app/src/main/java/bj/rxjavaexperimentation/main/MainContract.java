package bj.rxjavaexperimentation.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import java.util.List;

import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.order.Order;
import io.reactivex.Single;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View
    {
        void showLoading(boolean b);

        void setDrawer(Drawer drawer);

        // RecyclerView gets detached upon adding the NavigationDrawer
        void setupRecyclerView();

        void displayOrder(String id);

        void displayOrdersActivity(String username);

        void displayListingsActivity(String username);

        void displayListing(String listingId, String username, String title, String s, String username1);

        void displayError(boolean b);
    }

    interface Presenter
    {
        void connectAndBuildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar);

        Single<List<Order>> fetchOrders();

        Single<List<Listing>> fetchSelling();

        void setupRecyclerView(MainActivity mainActivity, RecyclerView recyclerView);
    }
}
