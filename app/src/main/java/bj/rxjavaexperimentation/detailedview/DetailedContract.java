package bj.rxjavaexperimentation.detailedview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public interface DetailedContract
{
    interface View
    {
        void showMemberDetails(String name, Integer id);
    }

    interface Presenter
    {
        void fetchDetailedInformation(String type, String id);

        void setupRecyclerView(RecyclerView rvDetailed, String title);
    }
}
