package bj.vinylbrowser.search

import android.support.design.widget.TabLayout
import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.greendao.SearchTerm
import bj.vinylbrowser.model.search.SearchResult
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTest {
    lateinit var presenter: SearchPresenter
    val mView: SearchContract.View = mock()
    val searchController: SearchController = mock()
    val searchModelFunc: Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> = mock()
    val testScheduler = TestScheduler()
    val daoManager: DaoManager = mock()
    val disposable: CompositeDisposable = mock()

    @Before
    fun setUp() {
        presenter = SearchPresenter(mView, searchController, searchModelFunc, TestSchedulerProvider(testScheduler), daoManager, disposable)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mView, searchController, searchModelFunc, daoManager, disposable)
    }

    @Test
    fun showPastSearchesTrue_showsPastSearches() {
        val emptyList = emptyList<SearchTerm>()
        whenever(daoManager.recentSearchTerms).thenReturn(emptyList)

        presenter.showPastSearches(true)

        verify(daoManager).recentSearchTerms
        verify(searchController).setPastSearches(emptyList)
    }

    @Test
    fun showPastSearchesFalse_doesntShowPastSearches() {
        presenter.showPastSearches(false)

        verify(searchController).showPastSearches = false
    }

    @Test
    fun disposeUnsub_disposesUnsubs() {
        presenter.unsubscribe()
        presenter.dispose()

        verify(disposable).clear()
        verify(disposable).dispose()
    }

    @Test
    @Throws(Exception::class)
    fun searchIntentEmptyList_displaysEmptyListNoFilter() {
        val emptyList = emptyList<SearchResult>()
        val mockTextEvent: SearchViewQueryTextEvent = mock()
        whenever(mockTextEvent.queryText()).thenReturn("yee")
        whenever(disposable.add(any<Disposable>())).thenReturn(true)
        whenever(mView.searchIntent()).thenReturn(Observable.just<SearchViewQueryTextEvent>(mockTextEvent))
        whenever(searchModelFunc.apply(mockTextEvent)).thenReturn(Observable.just(emptyList))

        presenter.setupSearchViewObserver()

        verify(disposable).add(any<Disposable>())
        verify(mView).searchIntent()

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions()

        verify(searchController).searching = true
        verify(mockTextEvent).queryText()
        verify(daoManager).storeSearchTerm("yee")
        verify(searchModelFunc).apply(mockTextEvent)
        verify(searchController).setSearchResults(emptyList)
        verifyNoMoreInteractions(mockTextEvent)
    }

    @Test
    @Throws(Exception::class)
    fun searchIntentError_displaysErrorButContinues() {
        val emptyList = emptyList<SearchResult>()
        val mockTextEvent: SearchViewQueryTextEvent = mock()
        whenever(mockTextEvent.queryText()).thenReturn("yee")
        whenever(disposable.add(any<Disposable>())).thenReturn(true)
        whenever(mView.searchIntent()).thenReturn(Observable.error<SearchViewQueryTextEvent>(Throwable()), Observable.just<SearchViewQueryTextEvent>(mockTextEvent))
        whenever(searchModelFunc.apply(mockTextEvent)).thenReturn(Observable.just<List<SearchResult>>(emptyList))

        presenter.setupSearchViewObserver()

        verify(disposable).add(any<Disposable>())
        verify(mView).searchIntent()

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions()

        // Check for this intent again as it's resubscribed onErrorResumeNext()
        verify(mView, times(2)).searchIntent()
        verify(searchController).searching = true
        verify(daoManager).storeSearchTerm("yee")
        verify(searchModelFunc).apply(mockTextEvent)
        verify(searchController).setSearchResults(emptyList)
        verify(mockTextEvent).queryText()
        verifyNoMoreInteractions(mockTextEvent)
    }

    @Test
    @Throws(Exception::class)
    fun setupTabEmitAll_setsUpTabObserver() {
        val mockTabEvent: TabLayoutSelectionEvent = mock()
        val mockTab: TabLayout.Tab = mock()
        whenever(disposable.add(any<Disposable>())).thenReturn(true)
        whenever(mView.tabIntent()).thenReturn(Observable.just<TabLayoutSelectionEvent>(mockTabEvent))
        whenever(mockTabEvent.tab()).thenReturn(mockTab)
        whenever(mockTab.text).thenReturn("all")

        presenter.setupTabObserver()

        verify(disposable).add(any<Disposable>())
        verify(mView).tabIntent()

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions()

        verify(mockTabEvent, times(1)).tab()
        verify(mockTab, times(1)).text
        verifyNoMoreInteractions(mockTabEvent)
    }

    /**
     * This emits an empty list and then a list with artist and release items.
     *
     *
     * It THEN activates a tab click.
     *
     *
     * This is one of the worst tests I have ever written. Pass me the eye bleach.

     * @throws Exception Ooh la la.
     */
    @Test
    @Throws(Exception::class)
    fun tabChangeArtist_DisplaysArtists() {
        val mockTabEvent: TabLayoutSelectionEvent = mock()
        val emptyList = emptyList<SearchResult>()
        val mockTab: TabLayout.Tab = mock()
        whenever(disposable.add(any<Disposable>())).thenReturn(true)
        whenever(mView.tabIntent()).thenReturn(Observable.just<TabLayoutSelectionEvent>(mockTabEvent))
        whenever(mockTabEvent.tab()).thenReturn(mockTab)
        whenever(mockTab.text).thenReturn("artist")

        val artistAndReleaseSearchResult = SearchFactory.buildArtistAndReleaseSearchResult()
        val mockTextEvent: SearchViewQueryTextEvent = mock()
        whenever(mockTextEvent.queryText()).thenReturn("yee1")
        val mockTextEvent2: SearchViewQueryTextEvent = mock()
        whenever(mockTextEvent2.queryText()).thenReturn("yee2")
        whenever(mView.searchIntent()).thenReturn(Observable.fromArray<SearchViewQueryTextEvent>(mockTextEvent, mockTextEvent2))
        whenever(searchModelFunc.apply(mockTextEvent)).thenReturn(Observable.just(emptyList))
        whenever(searchModelFunc.apply(mockTextEvent2)).thenReturn(Observable.just(artistAndReleaseSearchResult))
        whenever(searchController.showPastSearches).thenReturn(false)
        whenever(searchController.searching).thenReturn(false)

        presenter.setupSearchViewObserver()
        presenter.setupTabObserver()

        verify(disposable, times(2)).add(any<Disposable>())
        verify(mView).tabIntent()
        verify(mView).searchIntent()

        // At this point, successful subscription. Begin emitting
        testScheduler.triggerActions()

        verify(mockTabEvent, times(1)).tab()
        verify(mockTab, times(1)).text

        verify(searchController, times(2)).searching = true
        verify(daoManager).storeSearchTerm("yee1")
        verify(daoManager).storeSearchTerm("yee2")
        verify(mockTextEvent).queryText()
        verify(mockTextEvent2).queryText()
        verify(searchModelFunc).apply(mockTextEvent)
        verify(searchModelFunc).apply(mockTextEvent2)
        verify(searchController).setSearchResults(emptyList)
        verify(searchController).setSearchResults(artistAndReleaseSearchResult)
        verify(searchController).setSearchResults(Arrays.asList(artistAndReleaseSearchResult[0]))
        verify(searchController).showPastSearches
        verify(searchController).searching
        verifyNoMoreInteractions(mockTabEvent)
        verifyNoMoreInteractions(mockTextEvent)
    }
}