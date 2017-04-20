package bj.rxjavaexperimentation.artistreleases;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import bj.rxjavaexperimentation.artistreleases.fragments.ArtistReleasesAdapter;
import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import io.reactivex.functions.Consumer;

/**
 * Created by Josh Laird on 10/04/2017.
 */

public interface ArtistReleasesContract
{
    interface View
    {
        void launchDetailedActivity(String type, String title, String id);
    }

    interface Presenter
    {
        void getArtistReleases(String id);

        void setupViewPager(TabLayout tabLayout, ViewPager viewPager, FragmentManager supportFragmentManager);

        ArtistReleasesAdapter setupRecyclerView(RecyclerView recyclerView, FragmentActivity activity);

        void connectToBehaviorRelay(Consumer<List<ArtistRelease>> consumer, String searchFilter);

        void launchDetailedActivity(String type, String title, String id);
    }
}
