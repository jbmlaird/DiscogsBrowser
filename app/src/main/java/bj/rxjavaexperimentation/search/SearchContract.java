package bj.rxjavaexperimentation.search;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import io.reactivex.Observable;

/**
 * Created by Josh Laird on 20/02/2017.
 */

public interface SearchContract
{
    interface View
    {
        void hideProgressBar();

        void showProgressBar();

        Observable<SearchViewQueryTextEvent> searchIntent();
    }

    interface Presenter
    {
        void setupRecyclerView(RecyclerView rvResults);

        void goToResult(ImageView ivImage);

        void setupSubscription();
    }
}
