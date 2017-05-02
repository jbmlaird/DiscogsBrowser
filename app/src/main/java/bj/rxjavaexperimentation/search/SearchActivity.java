package bj.rxjavaexperimentation.search;

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

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artist.ArtistActivity;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.common.MyRecyclerView;
import bj.rxjavaexperimentation.label.LabelActivity;
import bj.rxjavaexperimentation.master.MasterActivity;
import bj.rxjavaexperimentation.model.search.SearchResult;
import bj.rxjavaexperimentation.release.ReleaseActivity;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 20/02/2017.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View
{
    private final String TAG = getClass().getSimpleName();
    @Inject SearchPresenter presenter;
    @Inject MySchedulerProvider mySchedulerProvider;
    @BindView(R.id.lytTabs) LinearLayout lytTabs;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pbRecyclerView) ProgressBar pbRecyclerView;
    @BindView(R.id.rvResults) MyRecyclerView rvResults;
    private SearchComponent component;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        component = DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build();
        component.inject(this);
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
        presenter.setupRecyclerView(rvResults);
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
        super.onResume();
        presenter.setupSubscriptions();
    }

    @OnClick(R.id.search_close_btn)
    public void onClose()
    {
        searchView.setQuery("", false);
        presenter.showPastSearches(true);
    }

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

    @Override
    public Observable<TabLayoutSelectionEvent> tabIntent()
    {
        return RxTabLayout.selectionEvents(tabLayout)
                .skip(1);
    }

    @Override
    public void startDetailedActivity(SearchResult searchResult)
    {
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
        searchView.setQuery(searchTerm, false);
    }

    @Override
    public void retry()
    {
        String query = searchView.getQuery().toString();
        searchView.setQuery("", false);
        searchView.setQuery(query, false);
    }
}
