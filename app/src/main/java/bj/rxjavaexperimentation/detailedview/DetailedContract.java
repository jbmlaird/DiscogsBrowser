package bj.rxjavaexperimentation.detailedview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public interface DetailedContract
{
    interface View
    {
        void showMemberDetails(String name, Integer id);

        void displayRelease(Integer id, String title);

        void displayLabelReleases(Integer id, String title);
    }

    interface Presenter
    {
        void fetchDetailedInformation(String type, String id);

        void setupRecyclerView(RecyclerView rvDetailed, String title, Toolbar toolbar);

        void displayLabelReleases(Integer labelId, String releasesUrl);

        void unsubscribe();
    }
}
