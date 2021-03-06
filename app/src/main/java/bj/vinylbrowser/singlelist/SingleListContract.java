package bj.vinylbrowser.singlelist;

import bj.vinylbrowser.common.BasePresenter;
import bj.vinylbrowser.common.SingleListView;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public interface SingleListContract
{
    interface View extends SingleListView
    {
        Observable<CharSequence> filterIntent();
    }

    interface Presenter extends BasePresenter
    {
        void getData(Integer stringId, String username);

        void setupFilterSubscription();
    }
}
