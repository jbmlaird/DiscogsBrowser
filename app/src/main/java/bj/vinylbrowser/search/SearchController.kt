package bj.vinylbrowser.search

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import bj.vinylbrowser.App
import bj.vinylbrowser.AppComponent
import bj.vinylbrowser.R
import bj.vinylbrowser.artist.ArtistController
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.customviews.MyRecyclerView
import bj.vinylbrowser.label.LabelController
import bj.vinylbrowser.master.MasterController
import bj.vinylbrowser.model.search.SearchResult
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.jakewharton.rxbinding2.support.design.widget.RxTabLayout
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search.view.*
import javax.inject.Inject

/**
 * Created by Josh Laird on 29/05/2017.
 */
class SearchController : BaseController(), SearchContract.View {
    @Inject lateinit var presenter: SearchPresenter
    @Inject lateinit var mySchedulerProvider: MySchedulerProvider
    @Inject lateinit var tracker: AnalyticsTracker
    @Inject lateinit var controller: SearchEpxController
    lateinit var tabLayout: TabLayout
    lateinit var searchView: SearchView
    lateinit var toolbar: Toolbar
    lateinit var rvResults: MyRecyclerView

    override fun setupComponent(appComponent: AppComponent) {
        appComponent
                .searchComponentBuilder()
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.unsubscribe()
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        tracker.send(applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.loaded), "onResume", "1")
        presenter.setupSearchViewObserver()
        presenter.setupTabObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        outState.putParcelableArrayList("searchResults", controller.searchResults as ArrayList<SearchResult>)
        super.onSaveViewState(view, outState)
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        controller.setSearchResults(savedViewState.get("searchResults") as MutableList<SearchResult>?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.activity_search, container, false)
        setupComponent(App.appComponent)
        tabLayout = view.tabLayout
        searchView = view.searchView
        toolbar = view.toolbar
        rvResults = view.recyclerView
        setupRecyclerView(rvResults, controller)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        controller.setPastSearches(presenter.recentSearchTerms)
        searchView.isIconified = false
        searchView.requestFocus()
        (searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text) as EditText).setTextColor(ContextCompat.getColor(applicationContext, android.R.color.white))
        searchView.findViewById(R.id.search_close_btn).setOnClickListener {
            tracker.send(applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.clicked), "searchClear", "1")
            searchView.setQuery("", false)
            presenter.showPastSearches(true)
        }
        val imm = applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
        return view
    }

    /**
     * Emits an RxBinding {@link Observable} that can be subscribed to to observe SearchView
     * text changes.
     *
     * @return SearchViewQueryTextEvent Observable.
     */
    override fun searchIntent(): Observable<SearchViewQueryTextEvent> {
        return RxSearchView.queryTextChangeEvents(searchView)
                .skipInitialValue()
                .debounce(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .subscribeOn(mySchedulerProvider.ui())
                .map { searchViewQueryTextEvent ->
                    if (searchViewQueryTextEvent.queryText().length <= 2)
                        presenter.showPastSearches(true)
                    else
                        presenter.showPastSearches(false)
                    searchViewQueryTextEvent
                }
                .filter { searchViewQueryTextEvent -> searchViewQueryTextEvent.queryText().length > 2 }
    }

    /**
     * Emits an Observable that can be subscribed to to watch tab click changes.
     *
     * @return TabLayoutSelectEvent Observable.
     */
    override fun tabIntent(): Observable<TabLayoutSelectionEvent> {
        return RxTabLayout.selectionEvents(tabLayout)
                .skip(1)
    }

    override fun startDetailedActivity(searchResult: SearchResult?) {
        tracker.send(applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.clicked), "detailedActivity", "1")
        val imm = applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        when (searchResult?.type) {
            "release" -> router.pushController(RouterTransaction.with(ReleaseController(searchResult.title, searchResult.id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "label" -> router.pushController(RouterTransaction.with(LabelController(searchResult.title, searchResult.id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "artist" -> router.pushController(RouterTransaction.with(ArtistController(searchResult.title, searchResult.id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
            "master" -> router.pushController(RouterTransaction.with(MasterController(searchResult.title, searchResult.id))
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
        }
    }

    override fun fillSearchBox(searchTerm: String?) {
        tracker.send(applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.clicked), "searchTerm", "1")
        searchView.setQuery(searchTerm, false)
    }

    override fun retry() {
        tracker.send(applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.search_activity), applicationContext?.getString(R.string.clicked), "retry", "1")
        val query = searchView.query.toString()
        searchView.setQuery("", false)
        searchView.setQuery(query, false)
    }
}