package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.detailedview.DetailedActivity;
import bj.rxjavaexperimentation.model.search.SearchResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 20/02/2017.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View
{
    private static final String TAG = "SearchActivity";

    @Inject SearchPresenter presenter;
    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pbRecyclerView) ProgressBar pbRecyclerView;
    @BindView(R.id.rvResults) RecyclerView rvResults;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        ButterKnife.bind(this);
        presenter.setupSubscription();
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
    public void hideProgressBar()
    {
        pbRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar()
    {
        pbRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public Observable<SearchViewQueryTextEvent> searchIntent()
    {
        return RxSearchView.queryTextChangeEvents(searchView)
                .debounce(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .filter(searchViewQueryTextEvent -> searchViewQueryTextEvent.queryText().length() > 2);
    }

    @Override
    public void startDetailedActivity(SearchResult searchResult, ImageView imageView)
    {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("title", searchResult.getTitle());
        intent.putExtra("type", searchResult.getType());
        intent.putExtra("id", searchResult.getId());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }
}
