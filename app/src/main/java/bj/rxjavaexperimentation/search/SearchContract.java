package bj.rxjavaexperimentation.search;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.search.epoxy.SearchResultModel_;
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

        void startDetailedActivity(SearchResult searchResult, ImageView imageView);
    }

    interface Presenter
    {
        void setupRecyclerView(RecyclerView rvResults);

        void viewDetailed(SearchResult searchResult, ImageView searchResultModel);

        void setupSubscription();
    }
}
