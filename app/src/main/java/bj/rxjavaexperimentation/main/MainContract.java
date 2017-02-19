package bj.rxjavaexperimentation.main;

import android.support.v7.widget.RecyclerView;

import bj.rxjavaexperimentation.common.BasePresenter;
import bj.rxjavaexperimentation.discogs.gson.release.Release;
import io.reactivex.Observable;

/**
 * Created by j on 18/02/2017.
 */

public interface MainContract
{
    interface View extends BaseView
    {
        void hideProgressBar();

        void showProgressBar();
    }

    interface Presenter extends BasePresenter<View>
    {
        void setupRecyclerView(RecyclerView rvResults);

        void addToRecyclerView(Observable<Release> value);
    }
}
