package bj.discogsbrowser.search;

import android.content.Context;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.List;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.di.scopes.ActivityScope;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
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
