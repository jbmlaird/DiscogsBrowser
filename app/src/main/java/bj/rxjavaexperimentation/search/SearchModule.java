package bj.rxjavaexperimentation.search;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 20/02/2017.
 */

@Module
public class SearchModule
{
    private static final String TAG = "SearchModule";
    private SearchContract.View mView;

    public SearchModule(SearchContract.View view)
    {
        mView = view;
    }

    @Provides
    SearchContract.View providesSearchView()
    {
        return mView;
    }

    @Provides
    @Singleton
    Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> providesSearchFunction(DiscogsInteractor discogsInteractor)
    {
        SearchResult startingSearchResult = new SearchResult();
        startingSearchResult.setId("bj");

        return searchViewQueryTextEvent ->
                discogsInteractor.searchDiscogs(searchViewQueryTextEvent.queryText().toString())
                        .startWith(Observable.just(Collections.singletonList(startingSearchResult)));
    }
}
