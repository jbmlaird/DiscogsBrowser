package bj.rxjavaexperimentation.search;

import android.support.v7.widget.RecyclerView;

import bj.rxjavaexperimentation.common.BasePresenter;
import bj.rxjavaexperimentation.common.BaseView;

/**
 * Created by Josh Laird on 20/02/2017.
 */

public interface SearchContract
{
    interface View extends BaseView
    {
        void hideProgressBar();

        void showProgressBar();
    }

    interface Presenter extends BasePresenter<View>
    {
        void setupRecyclerView(RecyclerView rvResults);

        void searchDiscogs(String query);
    }
}
