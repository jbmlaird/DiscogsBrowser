package bj.rxjavaexperimentation.singlelist;

import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public interface SingleListContract
{
    interface View
    {
        Observable<CharSequence> filterIntent();

        void stopLoading();
    }

    interface Presenter
    {
        void getData(String type, String username);

        void setupRecyclerView(SingleListActivity singleListActivity, RecyclerView recyclerView);

        void setupSubscription();
    }
}
