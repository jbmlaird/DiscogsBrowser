package bj.vinylbrowser.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public class OrderActivity extends BaseActivity implements OrderContract.View
{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @Inject OrderPresenter presenter;
    @Inject AnalyticsTracker tracker;
    @Inject OrderController controller;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .orderComponentBuilder()
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String id)
    {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("id"));
        setupRecyclerView(recyclerView, controller);
        presenter.fetchOrderDetails(getIntent().getStringExtra("id"));
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.order_activity), getString(R.string.order_activity), getString(R.string.loaded), "onResume", "1");
        super.onResume();
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.order_activity), getString(R.string.order_activity), getString(R.string.clicked), "retry", "1");
        presenter.fetchOrderDetails(getIntent().getStringExtra("id"));
    }
}