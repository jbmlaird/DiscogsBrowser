package bj.rxjavaexperimentation.singlelist;

import io.reactivex.Observable;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public interface SingleListContract
{
    interface View
    {
        Observable<CharSequence> filterIntent();
    }

    interface Presenter
    {
        void getData(String type, String username);
    }
}
