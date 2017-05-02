package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.entity.DaoSession;
import bj.rxjavaexperimentation.entity.SearchTerm;
import bj.rxjavaexperimentation.entity.SearchTermDao;
import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import es.dmoral.toasty.Toasty;
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
    private final SearchTermDao searchTermDao;
    private List<SearchResult> searchResults = new ArrayList<>();
    private String currentFilter = "all";
    private Context mContext;
    private SearchContract.View mView;
    private SearchController searchController;
    private Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchModelFunc;
    private CompositeDisposable disposable;
    private DisposableObserver<List<SearchResult>> searchObserver;

    @Inject
    public SearchPresenter(Context mContext, SearchContract.View mView, SearchController searchController, Function<SearchViewQueryTextEvent,
            ObservableSource<List<SearchResult>>> searchModelFunc, MySchedulerProvider mySchedulerProvider, DaoSession daoSession, CompositeDisposable disposable)
    {
        this.mContext = mContext;
        this.mView = mView;
        this.searchController = searchController;
        this.searchModelFunc = searchModelFunc;
        this.mySchedulerProvider = mySchedulerProvider;
        this.searchTermDao = daoSession.getSearchTermDao();
        this.disposable = disposable;
    }

    @Override
    public void setupRecyclerView(RecyclerView rvResults)
    {
        rvResults.setLayoutManager(new LinearLayoutManager(mContext));
        rvResults.setAdapter(searchController.getAdapter());
        searchController.setSearchTerms(searchTermDao.queryBuilder().orderDesc(SearchTermDao.Properties.Date).build().list());
    }

    @Override
    public void setupSubscriptions()
    {
        disposable.add(mView.searchIntent()
                .doOnNext(onNext -> searchController.setSearching(true))
                .observeOn(mySchedulerProvider.io())
                .map(this::storeSearchTerm)
                .flatMap(searchModelFunc)
                .observeOn(mySchedulerProvider.ui())
                .onErrorResumeNext(mView.searchIntent()
                        .flatMap(searchModelFunc))
                .observeOn(mySchedulerProvider.ui())
                .subscribeWith(getSearchObserver()));

        disposable.add(mView.tabIntent()
                .subscribeOn(mySchedulerProvider.ui())
                .subscribeWith(getTabObserver()));
    }

    private DisposableObserver<TabLayoutSelectionEvent> getTabObserver()
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
                Log.e("SearchPresenter", "tabLayout onComplete()");
            }
        };
    }

    @Override
    public void showPastSearches(boolean showPastSearches)
    {
        if (showPastSearches)
            searchController.setSearchTerms(searchTermDao.queryBuilder().orderDesc(SearchTermDao.Properties.Date).build().list());
        searchController.setShowPastSearches(showPastSearches);
    }

    private SearchViewQueryTextEvent storeSearchTerm(SearchViewQueryTextEvent queryTextEvent)
    {
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setSearchTerm(queryTextEvent.queryText().toString());
        searchTerm.setDate(new Date());
        searchTermDao.insertOrReplace(searchTerm);
        Log.d(TAG, "Stored new search term: " + searchTerm.getSearchTerm());
        return queryTextEvent;
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
                        searchController.setResults(o);
                    }
                    else
                    {
                        if (!currentFilter.equals("all"))
                            Single.just(o)
                                    .subscribeOn(mySchedulerProvider.io())
                                    .observeOn(mySchedulerProvider.io())
                                    .flattenAsObservable(results -> results)
                                    .filter(searchResult ->
                                            searchResult.getType().equals(currentFilter))
                                    .toList()
                                    .observeOn(mySchedulerProvider.ui())
                                    .subscribe(filteredResults ->
                                                    searchController.setResults(filteredResults),
                                            Throwable::printStackTrace);
                        else
                            searchController.setResults(o);
                    }
                }

                @Override
                public void onError(Throwable e)
                {
                    Log.e(TAG, "error");
                    e.printStackTrace();
                    Toasty.error(mContext, "Unable to fetch search results", Toast.LENGTH_SHORT, true).show();
                }

                @Override
                public void onComplete()
                {
                    Log.e(TAG, "complete");
                }
            };
        return searchObserver;
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
