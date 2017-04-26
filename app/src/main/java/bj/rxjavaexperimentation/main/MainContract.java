package bj.rxjavaexperimentation.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import bj.rxjavaexperimentation.model.user.UserDetails;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View
    {
        void stopLoading();

        void setDrawer(Drawer drawer);

        void setupRecyclerView();

        void displayOrder(String id);

        void displayOrdersActivity();

        void displayListingsActivity();

        void displayListing(String listingId);
    }

    interface Presenter
    {
        void buildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar);

        void setupRecyclerView(MainActivity mainActivity, RecyclerView recyclerView);

        void setupObservers();

        UserDetails getUserDetails();

        void unsubscribe();
    }
}
