package bj.vinylbrowser.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.support.design.widget.RxTabLayout;
import com.jakewharton.rxbinding2.support.design.widget.TabLayoutSelectionEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.artist.ArtistActivity;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.customviews.MyRecyclerView;
import bj.vinylbrowser.label.LabelActivity;
import bj.vinylbrowser.master.MasterActivity;
import bj.vinylbrowser.model.search.SearchResult;
import bj.vinylbrowser.release.ReleaseActivity;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 20/02/2017.
 * <p>
 * Activity containing Search capabilities.
 */
public class SearchActivity extends BaseActivity implements SearchContract.View
{
    @Inject SearchPresenter presenter;
    @Inject MySchedulerProvider mySchedulerProvider;
    @Inject AnalyticsTracker tracker;
    @Inject SearchController controller;
    @BindView(R.id.lytTabs) LinearLayout lytTabs;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pbRecyclerView) ProgressBar pbRecyclerView;
    @BindView(R.id.recyclerView) MyRecyclerView rvResults;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .searchComponentBuilder()
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context)
    {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);
        setupRecyclerView(rvResults, controller);
        controller.setPastSearches(presenter.getRecentSearchTerms());
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView.setIconified(false);
        searchView.requestFocus();
        // Set SearchView text color to white
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(ContextCompat.getColor(this, android.R.color.white));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.search_activity), getString(R.string.search_activity), getString(R.string.loaded), "onResume", "1");
        super.onResume();
        presenter.setupSearchViewObserver();
        presenter.setupTabObserver();
    }

    @OnClick(R.id.search_close_btn)
    public void onClose()
    {
        tracker.send(getString(R.string.search_activity), getString(R.string.search_activity), getString(R.string.clicked), "searchClear", "1");
        searchView.setQuery("", false);
        presenter.showPastSearches(true);
    }

    /**
     * Emits an RxBinding {@link Observable} that can be subscribed to to observe SearchView
     * text changes.
     *
     * @return SearchViewQueryTextEvent Observable.
     */
    @Override
    public Observable<SearchViewQueryTextEvent> searchIntent()
    {
        return RxSearchView.queryTextChangeEvents(searchView)
                .debounce(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .subscribeOn(mySchedulerProvider.ui())
                .map(searchViewQueryTextEvent ->
                {
                    if (searchViewQueryTextEvent.queryText().length() <= 2)
                        presenter.showPastSearches(true);
                    else
                        presenter.showPastSearches(false);
                    return searchViewQueryTextEvent;
                })
                .filter(searchViewQueryTextEvent -> searchViewQueryTextEvent.queryText().length() > 2);
    }

    /**
     * Emits an Observable that can be subscribed to to watch tab click changes.
     *
     * @return TabLayoutSelectEvent Observable.
     */
    @Override
    public Observable<TabLayoutSelectionEvent> tabIntent()
    {
        return RxTabLayout.selectionEvents(tabLayout)
                .skip(1);
    }

    @Override
    public void startDetailedActivity(SearchResult searchResult)
    {
        tracker.send(getString(R.string.search_activity), getString(R.string.search_activity), getString(R.string.clicked), "detailedActivity", "1");
        switch (searchResult.getType())
        {
            case "release":
                startActivity(ReleaseActivity.createIntent(this, searchResult.getTitle(), searchResult.getId()));
                break;
            case "label":
                startActivity(LabelActivity.createIntent(this, searchResult.getTitle(), searchResult.getId()));
                break;
            case "artist":
                startActivity(ArtistActivity.createIntent(this, searchResult.getTitle(), searchResult.getId()));
                break;
            case "master":
                startActivity(MasterActivity.createIntent(this, searchResult.getTitle(), searchResult.getId()));
                break;
        }
    }

    @Override
    public void fillSearchBox(String searchTerm)
    {
        tracker.send(getString(R.string.search_activity), getString(R.string.search_activity), getString(R.string.clicked), "searchTerm", "1");
        searchView.setQuery(searchTerm, false);
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.search_activity), getString(R.string.search_activity), getString(R.string.clicked), "retry", "1");
        String query = searchView.getQuery().toString();
        searchView.setQuery("", false);
        searchView.setQuery(query, false);
    }
}
