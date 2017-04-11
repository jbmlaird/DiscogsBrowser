package bj.rxjavaexperimentation.main;

import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View
    {

    }

    interface Presenter
    {
        Drawer buildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar);
    }
}
