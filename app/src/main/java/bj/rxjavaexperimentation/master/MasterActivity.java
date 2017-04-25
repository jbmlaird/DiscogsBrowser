package bj.rxjavaexperimentation.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.release.ReleaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class MasterActivity extends BaseActivity implements MasterContract.View
{
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject MasterPresenter presenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        MasterComponent component = DaggerMasterComponent.builder()
                .appComponent(appComponent)
                .masterModule(new MasterModule(this))
                .build();

        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        setupToolbar(toolbar);
        presenter.setupRecyclerView(this, recyclerView, getIntent().getStringExtra("title"));
        presenter.getData(getIntent().getStringExtra("id"));
    }

    @Override
    public void displayRelease(String title, String id)
    {
        Intent intent = new Intent(this, ReleaseActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
