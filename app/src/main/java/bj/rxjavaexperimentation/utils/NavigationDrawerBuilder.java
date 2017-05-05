package bj.rxjavaexperimentation.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.about.AboutActivity;
import bj.rxjavaexperimentation.login.LoginActivity;
import bj.rxjavaexperimentation.main.MainActivity;
import bj.rxjavaexperimentation.search.SearchActivity;
import bj.rxjavaexperimentation.singlelist.SingleListActivity;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@Singleton
public class NavigationDrawerBuilder
{
    private Context context;
    private SharedPrefsManager sharedPrefsManager;

    @Inject
    public NavigationDrawerBuilder(Context context, SharedPrefsManager sharedPrefsManager)
    {
        this.context = context;
        this.sharedPrefsManager = sharedPrefsManager;
    }

    public Drawer buildNavigationDrawer(MainActivity activity, Toolbar toolbar)
    {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(ContextCompat.getDrawable(context, R.drawable.gradient))
                .addProfiles(
                        new ProfileDrawerItem().withName(sharedPrefsManager.getName()).withEmail(sharedPrefsManager.getUsername()).withIcon(sharedPrefsManager.getAvatarUrl())
                )
                .build();

        return new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(context.getString(R.string.drawer_item_collection))
                                .withIcon(R.drawable.ic_library_music_black_24dp)
                                .withIdentifier(1).withSelectable(false).withBadge(String.valueOf(sharedPrefsManager.getNumCollection())).withBadgeStyle(new BadgeStyle().withTextColor(ContextCompat.getColor(context, android.R.color.white)).withColorRes(R.color.colorAccent)),
                        new PrimaryDrawerItem().withName(context.getString(R.string.drawer_item_wantlist))
                                .withIcon(R.drawable.ic_remove_red_eye_black_24dp)
                                .withIdentifier(2).withSelectable(false).withBadge(String.valueOf(sharedPrefsManager.getNumWantlist())).withBadgeStyle(new BadgeStyle().withTextColor(ContextCompat.getColor(context, android.R.color.white)).withColorRes(R.color.colorAccent)),
                        new ExpandableDrawerItem().withName("Marketplace")
                                .withIcon(R.drawable.ic_attach_money_black_24dp)
                                .withIdentifier(3).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName(context.getString(R.string.selling)).withLevel(2)
                                        .withIdentifier(3001),
                                new SecondaryDrawerItem().withName("Orders").withLevel(2)
                                        .withIdentifier(3002)
                        ),
                        new PrimaryDrawerItem().withName(context.getString(R.string.drawer_item_search))
                                .withIcon(R.drawable.ic_search_black_24dp)
                                .withIdentifier(4).withSelectable(false),
//                        new PrimaryDrawerItem().withName(context.getString(R.string.drawer_item_settings))
//                                .withIcon(R.drawable.ic_settings_black_24dp)
//                                .withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName(context.getString(R.string.drawer_item_about))
                                .withIcon(R.drawable.ic_info_outline_black_24dp)
                                .withIdentifier(6).withSelectable(false),
                        new PrimaryDrawerItem().withName(context.getString(R.string.drawer_item_logout))
                                .withIcon(R.drawable.ic_exit_to_app_black_24dp)
                                .withIdentifier(7).withSelectable(false)
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) ->
                        {
                            switch ((int) drawerItem.getIdentifier())
                            {
                                case 1:
                                    activity.startActivity(SingleListActivity.createIntent(activity, "collection", sharedPrefsManager.getUsername()));
                                    break;
                                case 2:
                                    activity.startActivity(SingleListActivity.createIntent(activity, "wantlist", sharedPrefsManager.getUsername()));
                                    break;
                                case 3001:
                                    activity.startActivity(SingleListActivity.createIntent(activity, "selling", sharedPrefsManager.getUsername()));
                                    break;
                                case 3002:
                                    activity.startActivity(SingleListActivity.createIntent(activity, "orders", sharedPrefsManager.getUsername()));
                                    break;
                                case 4:
                                    activity.startActivity(SearchActivity.createIntent(activity));
                                    break;
//                                case 5:
//                                    // TODO: Implement Settings
//                                    break;
                                case 6:
                                    activity.startActivity(AboutActivity.createIntent(activity));
                                    break;
                                case 7:
                                    RxSocialConnect.closeConnections();
                                    sharedPrefsManager.removeUserDetails();
                                    activity.startActivity(LoginActivity.createIntent(activity));
                                    activity.finish();
                                    break;
                            }
                            return false;
                        }
                )
                .build();
    }
}
