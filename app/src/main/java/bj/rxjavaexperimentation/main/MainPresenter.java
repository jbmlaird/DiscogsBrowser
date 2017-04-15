package bj.rxjavaexperimentation.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import javax.inject.Inject;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;

/**
 * Created by j on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private static final String TAG = "MainPresenter";
    private MainContract.View mView;
    private SharedPrefsManager sharedPrefsManager;

    @Inject
    public MainPresenter(MainContract.View view, SharedPrefsManager sharedPrefsManager)
    {
        mView = view;
        this.sharedPrefsManager = sharedPrefsManager;
    }

    // Move to NavigationDrawerBuilder class?
    @Override
    public Drawer buildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar)
    {
        return new DrawerBuilder()
                .withActivity(mainActivity)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_collection)
                                .withIcon(R.drawable.ic_library_music_black_24dp)
                                .withIdentifier(1).withSelectable(false).withBadge("20").withBadgeStyle(new BadgeStyle().withTextColor(ContextCompat.getColor(mainActivity, android.R.color.white)).withColorRes(R.color.colorAccent)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_wantlist)
                                .withIcon(R.drawable.ic_remove_red_eye_black_24dp)
                                .withIdentifier(2).withSelectable(false),
                        new ExpandableDrawerItem().withName("Marketplace")
                                .withIcon(R.drawable.ic_attach_money_black_24dp)
                                .withIdentifier(3).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Purchases").withLevel(2)
                                        .withIdentifier(2002),
                                new SecondaryDrawerItem().withName("Orders").withLevel(2)
                                        .withIdentifier(2003)
                        ),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search)
                                .withIcon(R.drawable.ic_search_black_24dp)
                                .withIdentifier(4).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings)
                                .withIcon(R.drawable.ic_settings_black_24dp)
                                .withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_about)
                                .withIcon(R.drawable.ic_info_outline_black_24dp)
                                .withIdentifier(6).withSelectable(false)
                )
                .build();
    }
}
