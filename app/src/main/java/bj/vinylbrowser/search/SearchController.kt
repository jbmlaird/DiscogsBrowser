package bj.vinylbrowser.search

import android.content.Context
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
import bj.vinylbrowser.artist.ArtistActivity
import bj.vinylbrowser.common.BaseController
import bj.vinylbrowser.customviews.MyRecyclerView
import bj.vinylbrowser.master.MasterActivity
import bj.vinylbrowser.model.search.SearchResult
import bj.vinylbrowser.release.ReleaseActivity
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider
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
        when (searchResult?.type) {
            "release" -> startActivity(ReleaseActivity.createIntent(applicationContext, searchResult?.title, searchResult?.id))
//            "label" -> startActivity(LabelController.createIntent(applicationContext, searchResult?.title, searchResult?.id))
            "artist" -> startActivity(ArtistActivity.createIntent(applicationContext, searchResult?.title, searchResult?.id))
            "master" -> startActivity(MasterActivity.createIntent(applicationContext, searchResult?.title, searchResult?.id))
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