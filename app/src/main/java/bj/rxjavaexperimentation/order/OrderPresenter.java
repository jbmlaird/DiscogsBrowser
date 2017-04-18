package bj.rxjavaexperimentation.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.order.epoxy.OrderController;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public class OrderPresenter implements OrderContract.Presenter
{
    private OrderContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private OrderController orderController;

    @Inject
    public OrderPresenter(OrderContract.View view, SearchDiscogsInteractor searchDiscogsInteractor, MySchedulerProvider mySchedulerProvider, OrderController orderController)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.orderController = orderController;
    }

    @Override
    public void fetchOrderDetails(String orderId)
    {
        searchDiscogsInteractor.fetchOrderDetails(orderId)
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
