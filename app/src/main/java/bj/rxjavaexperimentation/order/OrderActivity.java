package bj.rxjavaexperimentation.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
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

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        OrderComponent component = DaggerOrderComponent.builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build();
        component.inject(this);
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
        presenter.setupRecyclerView(this, recyclerView);
        presenter.fetchOrderDetails(getIntent().getStringExtra("id"));
    }

    @Override
    public void retry()
    {
        presenter.fetchOrderDetails(getIntent().getStringExtra("id"));
    }
}
