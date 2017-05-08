package bj.discogsbrowser.singlelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import bj.discogsbrowser.common.BasePresenter;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public interface SingleListContract
{
    interface View
    {
        Observable<CharSequence> filterIntent();

        void launchDetailedActivity(String type, String title, String id);

        // TODO: Change since Epoxy migration?
        // Activity context for pre-Android 5.1
        Context getActivityContext();
    }

    interface Presenter extends BasePresenter
    {
        void getData(Integer stringId, String username);

        void setupRecyclerView(SingleListActivity singleListActivity, RecyclerView recyclerView);

        void setupFilterSubscription();
    }
}
