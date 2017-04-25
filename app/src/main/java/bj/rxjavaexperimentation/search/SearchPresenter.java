package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.entity.DaoSession;
import bj.rxjavaexperimentation.entity.SearchTerm;
import bj.rxjavaexperimentation.entity.SearchTermDao;
import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import io.reactivex.ObservableSource;
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

    private Context mContext;
    private SearchContract.View mView;
    private SearchController searchController;
    private Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchModelFunc;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public SearchPresenter(Context mContext, SearchContract.View mView, SearchController searchController, Function<SearchViewQueryTextEvent,
            ObservableSource<List<SearchResult>>> searchModelFunc, MySchedulerProvider mySchedulerProvider, DaoSession daoSession)
    {
        this.mContext = mContext;
        this.mView = mView;
        this.searchController = searchController;
        this.searchModelFunc = searchModelFunc;
        this.mySchedulerProvider = mySchedulerProvider;
        this.searchTermDao = daoSession.getSearchTermDao();
    }

    @Override
    public void setupRecyclerView(RecyclerView rvResults)
    {
        rvResults.setLayoutManager(new LinearLayoutManager(mContext));
        rvResults.setAdapter(searchController.getAdapter());
        searchController.setSearchTerms(searchTermDao.queryBuilder().orderDesc(SearchTermDao.Properties.Date).build().list());
    }

    @Override
    public void setupSubscription()
    {
        disposable.add(mView.searchIntent()
                .observeOn(mySchedulerProvider.io())
                .map(this::storeSearchTerm)
                .flatMap(searchModelFunc)
                .observeOn(mySchedulerProvider.ui())
                .onErrorResumeNext(mView.searchIntent()
                        .flatMap(searchModelFunc))
                .observeOn(mySchedulerProvider.ui())
                .subscribeWith(getDisposableObserver()));
    }

    @Override
    public void showSuggestions()
    {
        searchController.setSearchTerms(searchTermDao.queryBuilder().orderDesc(SearchTermDao.Properties.Date).build().list());
        searchController.clearResults();
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

    private DisposableObserver<List<SearchResult>> getDisposableObserver()
    {
        return new DisposableObserver<List<SearchResult>>()
        {
            @Override
            public void onNext(List<SearchResult> o)
            {
                Log.e(TAG, "ye");
                Log.e(TAG, String.valueOf(o.size()));
                if (o.size() == 0)
                {
                    // Show no results
                    searchController.setResults(o);
                }
                else if (o.get(0).getId().equals("bj"))
                {
                    // New search
                    mView.showProgressBar();
                    searchController.setResults(o);
                }
                else
                {
                    mView.hideProgressBar();
                    searchController.setResults(o);
                }
            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(TAG, "error");
                e.printStackTrace();
                mView.hideProgressBar();
                // TODO: Show error
            }

            @Override
            public void onComplete()
            {
                Log.e(TAG, "complete");
            }
        };
    }
}
