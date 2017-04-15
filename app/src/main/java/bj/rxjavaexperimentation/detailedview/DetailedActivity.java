package bj.rxjavaexperimentation.detailedview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.singlelist.SingleListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 07/04/2017.
 */

public class DetailedActivity extends BaseActivity implements DetailedContract.View
{
    private static final String TAG = "DetailedActivity";
    @BindView(R.id.rvDetailed) RecyclerView rvDetailed;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject DetailedPresenter presenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        DetailedComponent component = DaggerDetailedComponent.builder()
                .appComponent(appComponent)
                .detailedModule(new DetailedModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        ButterKnife.bind(this);
        presenter.setupRecyclerView(rvDetailed, getIntent().getStringExtra("title"), toolbar);
        presenter.fetchDetailedInformation(getIntent().getStringExtra("type"), getIntent().getStringExtra("id"));
        setupActionBar(toolbar);
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

    @Override
    public void showMemberDetails(String name, Integer id)
    {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("title", name);
        intent.putExtra("type", "artist");
        intent.putExtra("id", String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public void displayRelease(Integer id, String title)
    {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", "release");
        intent.putExtra("id", String.valueOf(id));
        startActivity(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void displayLabelReleases(Integer id, String title)
    {
        // TODO: Implement
        Intent intent = new Intent(this, SingleListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", "label");
        intent.putExtra("id", String.valueOf(id));
        startActivity(intent);
    }
}
