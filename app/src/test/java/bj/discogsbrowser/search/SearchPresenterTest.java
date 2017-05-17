package bj.discogsbrowser.search;

import android.support.design.widget.TabLayout;

import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import edu.emory.mathcs.backport.java.util.Collections;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 02/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterTest
{
    private SearchPresenter presenter;
    @Mock SearchContract.View mView;
    @Mock SearchController searchController;
    @Mock Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchModelFunc;
    TestScheduler testScheduler = new TestScheduler();
    @Mock DaoManager daoManager;
    @Mock CompositeDisposable disposable;
    private SearchFactory searchFactory = new SearchFactory();

    @Before
    public void setUp()
    {
        presenter = new SearchPresenter(mView, searchController, searchModelFunc, new TestSchedulerProvider(testScheduler), daoManager, disposable);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(mView, searchController, searchModelFunc, daoManager, disposable);
    }

    @Test
    public void showPastSearchesTrue_showsPastSearches()
    {
        List<SearchTerm> emptyList = java.util.Collections.emptyList();
        when(daoManager.getRecentSearchTerms()).thenReturn(emptyList);

        presenter.showPastSearches(true);

        verify(daoManager).getRecentSearchTerms();
        verify(searchController).setPastSearches(emptyList);
        verify(searchController).setShowPastSearches(true);
    }

    @Test
    public void showPastSearchesFalse_doesntShowPastSearches()
    {
        presenter.showPastSearches(false);

        verify(searchController).setShowPastSearches(false);
    }

    @Test
    public void disposeUnsub_disposesUnsubs()
    {
        presenter.unsubscribe();
        presenter.dispose();

        verify(disposable).clear();
        verify(disposable).dispose();
    }

    @Test
    public void searchIntentEmptyList_displaysEmptyListNoFilter() throws Exception
    {
        List<SearchResult> emptyList = java.util.Collections.emptyList();
        SearchViewQueryTextEvent mockTextEvent = mock(SearchViewQueryTextEvent.class);
        when(mockTextEvent.queryText()).thenReturn("yee");
        when(disposable.add(any())).thenReturn(true);
        when(mView.searchIntent()).thenReturn(Observable.just(mockTextEvent));
        when(searchModelFunc.apply(mockTextEvent)).thenReturn(Observable.just(emptyList));

        presenter.setupSearchViewObserver();

        verify(disposable).add(any());
        verify(mView).searchIntent();

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions();

        verify(searchController).setSearching(true);
        verify(daoManager).storeSearchTerm("yee");
        verify(searchModelFunc).apply(mockTextEvent);
        verify(searchController).setSearchResults(emptyList);
        verifyNoMoreInteractions(mockTextEvent);
    }

    @Test
    public void searchIntentError_displaysErrorButContinues() throws Exception
    {
        List<SearchResult> emptyList = Collections.emptyList();
        SearchViewQueryTextEvent mockTextEvent = mock(SearchViewQueryTextEvent.class);
        when(mockTextEvent.queryText()).thenReturn("yee");
        when(disposable.add(any())).thenReturn(true);
        when(mView.searchIntent()).thenReturn(Observable.error(new Throwable()), Observable.just(mockTextEvent));
        when(searchModelFunc.apply(mockTextEvent)).thenReturn(Observable.just(emptyList));

        presenter.setupSearchViewObserver();

        verify(disposable).add(any());
        verify(mView).searchIntent();

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions();

        // Check for this intent again as it's resubscribed onErrorResumeNext()
        verify(mView, times(2)).searchIntent();
        verify(searchController).setSearching(true);
        verify(daoManager).storeSearchTerm("yee");
        verify(searchModelFunc).apply(mockTextEvent);
        verify(searchController).setSearchResults(emptyList);
        verifyNoMoreInteractions(mockTextEvent);
    }

    @Test
    public void setupTabEmitAll_setsUpTabObserver() throws Exception
    {
        TabLayoutSelectionEvent mockTabEvent = mock(TabLayoutSelectionEvent.class);
        TabLayout.Tab mockTab = mock(TabLayout.Tab.class);
        when(disposable.add(any())).thenReturn(true);
        when(mView.tabIntent()).thenReturn(Observable.just(mockTabEvent));
        when(mockTabEvent.tab()).thenReturn(mockTab);
        when(mockTab.getText()).thenReturn("all");

        presenter.setupTabObserver();

        verify(disposable).add(any());
        verify(mView).tabIntent();

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions();

        verify(mockTabEvent, times(1)).tab();
        verify(mockTab, times(1)).getText();
        verifyNoMoreInteractions(mockTabEvent);
    }

    /**
     * This emits an empty list and then a list with artist and release items.
     * <p>
     * It THEN activates a tab click.
     * <p>
     * This is one of the worst tests I have ever written. Pass me the eye bleach.
     *
     * @throws Exception Ooh la la.
     */
    @Test
    public void tabChangeArtist_DisplaysArtists() throws Exception
    {
        TabLayoutSelectionEvent mockTabEvent = mock(TabLayoutSelectionEvent.class);
        List<SearchResult> emptyList = java.util.Collections.emptyList();
        TabLayout.Tab mockTab = mock(TabLayout.Tab.class);
        when(disposable.add(any())).thenReturn(true);
        when(mView.tabIntent()).thenReturn(Observable.just(mockTabEvent));
        when(mockTabEvent.tab()).thenReturn(mockTab);
        when(mockTab.getText()).thenReturn("artist");

        List<SearchResult> artistAndReleaseSearchResult = searchFactory.getArtistAndReleaseSearchResult();
        SearchViewQueryTextEvent mockTextEvent = mock(SearchViewQueryTextEvent.class);
        when(mockTextEvent.queryText()).thenReturn("yee1");
        SearchViewQueryTextEvent mockTextEvent2 = mock(SearchViewQueryTextEvent.class);
        when(mockTextEvent2.queryText()).thenReturn("yee2");
        when(mView.searchIntent()).thenReturn(Observable.fromArray(mockTextEvent, mockTextEvent2));
        when(searchModelFunc.apply(mockTextEvent)).thenReturn(Observable.just(emptyList));
        when(searchModelFunc.apply(mockTextEvent2)).thenReturn(Observable.just(artistAndReleaseSearchResult));
        when(searchController.getShowPastSearches()).thenReturn(false);
        when(searchController.getSearching()).thenReturn(false);

        presenter.setupSearchViewObserver();
        presenter.setupTabObserver();

        verify(disposable, times(2)).add(any());
        verify(mView).tabIntent();
        verify(mView).searchIntent();

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions();

        verify(mockTabEvent, times(1)).tab();
        verify(mockTab, times(1)).getText();

        verify(searchController, times(2)).setSearching(true);
        verify(daoManager).storeSearchTerm("yee1");
        verify(daoManager).storeSearchTerm("yee2");
        verify(searchModelFunc).apply(mockTextEvent);
        verify(searchModelFunc).apply(mockTextEvent2);
        verify(searchController).setSearchResults(emptyList);
        verify(searchController).setSearchResults(artistAndReleaseSearchResult);
        verify(searchController).setSearchResults(Arrays.asList(artistAndReleaseSearchResult.get(0)));
        verify(searchController).getShowPastSearches();
        verify(searchController).getSearching();
        verifyNoMoreInteractions(mockTabEvent);
        verifyNoMoreInteractions(mockTextEvent);
    }
}
