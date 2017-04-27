package bj.rxjavaexperimentation.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.epoxy.order.OrderController;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public class OrderPresenter implements OrderContract.Presenter
{
    private OrderContract.View view;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private OrderController orderController;

    @Inject
    public OrderPresenter(OrderContract.View view, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, OrderController orderController)
    {
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.orderController = orderController;
    }

    @Override
    public void fetchOrderDetails(String orderId)
    {
        discogsInteractor.fetchOrderDetails(orderId)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(orderDetails ->
                        {
                            view.hideLoading();
                            orderController.setOrderDetails(orderDetails);
                        },
                        error ->
                                orderController.errorFetchingDetails());
    }

    public void setupRecyclerView(OrderActivity orderActivity, RecyclerView recyclerView)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(orderActivity));
        recyclerView.setAdapter(orderController.getAdapter());
    }
}
