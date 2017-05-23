package bj.vinylbrowser.search;

import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.List;

import bj.vinylbrowser.common.BasePresenter;
import bj.vinylbrowser.greendao.SearchTerm;
import bj.vinylbrowser.model.search.SearchResult;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 20/02/2017.
 */

public interface SearchContract
{
    interface View
    {
        Observable<SearchViewQueryTextEvent> searchIntent();

        Observable<TabLayoutSelectionEvent> tabIntent();

        void startDetailedActivity(SearchResult searchResult);

        void fillSearchBox(String searchTerm);

        void retry();
    }

    interface Presenter extends BasePresenter
    {
        void setupSearchViewObserver();

        void setupTabObserver();

        void showPastSearches(boolean b);

        List<SearchTerm> getRecentSearchTerms();
    }
}
