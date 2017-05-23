package bj.vinylbrowser.search;

import android.content.Context;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.List;

import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.model.search.SearchResult;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    protected SearchContract.View providesSearchView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    protected CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    protected Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> providesSearchFunction(DiscogsInteractor discogsInteractor)
    {
        return searchViewQueryTextEvent ->
                discogsInteractor.searchDiscogs(searchViewQueryTextEvent.queryText().toString()).toObservable();
    }

    @Provides
    @ActivityScope
    protected SearchController provideController(Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new SearchController(context, mView, imageViewAnimator, tracker);
    }

    @Provides
    @ActivityScope
    protected SearchPresenter provideSearchPresenter(SearchController controller, Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchFunc,
                                                     MySchedulerProvider mySchedulerProvider, DaoManager daoManager, CompositeDisposable disposable)
    {
        return new SearchPresenter(mView, controller, searchFunc, mySchedulerProvider, daoManager, disposable);
    }
}
