package bj.discogsbrowser.search;

import android.content.Context;

import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Josh Laird on 20/02/2017.
 */
public class SearchPresenter implements SearchContract.Presenter
{
    private static final String TAG = "SearchPresenter";
    private MySchedulerProvider mySchedulerProvider;
    private List<SearchResult> searchResults = new ArrayList<>();
    private String currentFilter = "all";
    private Context mContext;
    private SearchContract.View mView;
    private SearchController searchController;
    private Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchModelFunc;
    private DaoManager daoManager;
    private CompositeDisposable disposable;
    private DisposableObserver<List<SearchResult>> searchObserver;

    public SearchPresenter(Context mContext, SearchContract.View mView, SearchController searchController, Function<SearchViewQueryTextEvent,
            ObservableSource<List<SearchResult>>> searchModelFunc, MySchedulerProvider mySchedulerProvider, DaoManager daoManager, CompositeDisposable disposable)
    {
        this.mContext = mContext;
        this.mView = mView;
        this.searchController = searchController;
        this.searchModelFunc = searchModelFunc;
        this.mySchedulerProvider = mySchedulerProvider;
        this.daoManager = daoManager;
        this.disposable = disposable;
    }

    @Override
    public void setupSearchViewObserver()
    {
        disposable.add(getSearchIntent()
                .subscribeWith(getSearchObserver()));
    }

    @Override
    public void setupTabObserver()
    {
        disposable.add(mView.tabIntent()
                .subscribeOn(mySchedulerProvider.ui())
                .subscribeWith(getTabObserver()));
    }

    /**
     * Sets up an Observer on the SearchView.
     *
     * @return Observable to be subscribed to.
     */
    public Observable<List<SearchResult>> getSearchIntent()
    {
        return mView.searchIntent()
                .subscribeOn(mySchedulerProvider.io())
                .doOnNext(onNext ->
                {
                    searchController.setSearching(true);
                    daoManager.storeSearchTerm(onNext);
                })
                .switchMap(searchModelFunc)
                .doOnError(throwable ->
                {
                    if (throwable.getCause().getCause() != null && !throwable.getCause().getCause().getMessage().equals("thread interrupted"))
                        searchController.setError(true);
                    // Else ignore. The user has just searched again and interrupted the thread
                })
                .onErrorResumeNext(Observable.defer(this::getSearchIntent));
    }

    private DisposableObserver<List<SearchResult>> getSearchObserver()
    {
        if (searchObserver == null || searchObserver.isDisposed())
            searchObserver = new DisposableObserver<List<SearchResult>>()
            {
                @Override
                public void onNext(List<SearchResult> o)
                {
                    searchResults = o;
                    if (o.size() == 0)
                    {
                        // Show no results
                        searchController.setSearchResults(o);
                    }
                    else
                    {
                        if (!currentFilter.equals("all"))
                            Single.just(o)
                                    .subscribeOn(mySchedulerProvider.computation())
                                    .flattenAsObservable(results -> results)
                                    .filter(searchResult ->
                                            searchResult.getType().equals(currentFilter))
                                    .toList()
                                    .observeOn(mySchedulerProvider.ui())
                                    .subscribe(filteredResults ->
                                                    searchController.setSearchResults(filteredResults),
                                            Throwable::printStackTrace);
                        else
                            searchController.setSearchResults(o);
                    }
                }

                @Override
                public void onError(Throwable e)
                {
                    // Will never be reached as I have a onErrorResumeNext()
                    e.printStackTrace();
                    searchController.setError(true);
                }

                @Override
                public void onComplete()
                {
                }
            };
        return searchObserver;
    }

    /**
     * Sets up an Observer on the TabLayout.
     *
     * @return Observer subscribed to the TabLayout.
     */
    public DisposableObserver<TabLayoutSelectionEvent> getTabObserver()
    {
        return new DisposableObserver<TabLayoutSelectionEvent>()
        {
            @Override
            public void onNext(TabLayoutSelectionEvent tabLayoutSelectionEvent)
            {
                String currentTabText = tabLayoutSelectionEvent.tab().getText().toString().toLowerCase();
                if (!currentFilter.equals(currentTabText))
                {
                    currentFilter = currentTabText;
                    if (!searchController.getShowPastSearches() && !searchController.getSearching())
                        searchObserver.onNext(searchResults);
                }
            }

            @Override
            public void onError(Throwable e)
            {
                e.printStackTrace();
            }

            @Override
            public void onComplete()
            {
            }
        };
    }

    @Override
    public void showPastSearches(boolean showPastSearches)
    {
        if (showPastSearches)
            searchController.setPastSearches(daoManager.getRecentSearchTerms());
        searchController.setShowPastSearches(showPastSearches);
    }

    @Override
    public List<SearchTerm> getRecentSearchTerms()
    {
        return daoManager.getRecentSearchTerms();
    }

    @Override
    public void unsubscribe()
    {
        disposable.clear();
    }

    @Override
    public void dispose()
    {
        disposable.dispose();
    }
}
