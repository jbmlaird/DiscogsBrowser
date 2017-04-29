package bj.rxjavaexperimentation.order;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("orderId"));
        presenter.setupRecyclerView(this, recyclerView);
        presenter.fetchOrderDetails(getIntent().getStringExtra("orderId"));
    }
}
