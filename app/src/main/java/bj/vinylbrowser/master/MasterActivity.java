package bj.vinylbrowser.master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.customviews.MyRecyclerView;
import bj.vinylbrowser.release.ReleaseActivity;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 23/04/2017.
 * <p>
 * Activity to display {@link bj.vinylbrowser.model.master.Master} information.
 */
public class MasterActivity extends BaseActivity implements MasterContract.View
{
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject MasterPresenter presenter;
    @Inject AnalyticsTracker tracker;
    @Inject MasterController controller;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .masterComponentBuilder()
                .masterActivityModule(new MasterModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String title, String id)
    {
        Intent intent = new Intent(context, MasterActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        setupRecyclerView(recyclerView, controller, getIntent().getStringExtra("title"));
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.master_activity), getString(R.string.master_activity), getString(R.string.loaded), "onResume", "1");
        super.onResume();
    }

    @Override
    public void displayRelease(String title, String id)
    {
        tracker.send(getString(R.string.master_activity), getString(R.string.master_activity), getString(R.string.clicked), "release", "1");
        startActivity(ReleaseActivity.createIntent(this, title, id));
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.master_activity), getString(R.string.master_activity), getString(R.string.clicked), "retry", "1");
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    private void setupRecyclerView(MyRecyclerView recyclerView, MasterController controller, String title)
    {
        setupRecyclerView(recyclerView, controller);
        controller.setTitle(title);
    }
}
