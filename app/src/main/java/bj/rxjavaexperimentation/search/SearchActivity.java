package bj.rxjavaexperimentation.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 20/02/2017.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View
{
    private static final String TAG = "SearchActivity";

    @Inject SearchPresenter presenter;
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
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        presenter.setView(this);
        presenter.setupRecyclerView(rvResults);
        handleIntent(getIntent());
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
    public AppCompatActivity getActivity()
    {
        return this;
    }


    @Override
    protected void onNewIntent(Intent intent)
    {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e(TAG, query);
            //use the query to search your data somehow

            presenter.searchDiscogs(query);
        }
    }
}
