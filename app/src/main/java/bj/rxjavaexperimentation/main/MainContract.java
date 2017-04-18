package bj.rxjavaexperimentation.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

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
    }

    interface Presenter
    {
        void buildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar);

        void setupRecyclerView(MainActivity mainActivity, RecyclerView recyclerView);
    }
}
