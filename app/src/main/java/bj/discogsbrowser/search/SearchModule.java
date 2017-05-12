package bj.discogsbrowser.search;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.List;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.network.DiscogsInteractor;
import dagger.Module;
import dagger.Provides;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

/**
 * Created by Josh Laird on 20/02/2017.
 */

@Module
public class SearchModule
{
    private final String TAG = getClass().getSimpleName();
    private SearchContract.View mView;

    public SearchModule(SearchContract.View view)
    {
        mView = view;
    }

    @Provides
    @ActivityScope
    SearchContract.View providesSearchView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> providesSearchFunction(DiscogsInteractor discogsInteractor)
    {
        return searchViewQueryTextEvent ->
                discogsInteractor.searchDiscogs(searchViewQueryTextEvent.queryText().toString()).toObservable();
    }
}
