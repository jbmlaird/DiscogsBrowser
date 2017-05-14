package bj.discogsbrowser.order;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public class OrderPresenter implements OrderContract.Presenter
{
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private OrderController orderController;

    public OrderPresenter(DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, OrderController orderController)
    {
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.orderController = orderController;
    }

    @Override
    public void fetchOrderDetails(String orderId)
    {
        discogsInteractor.fetchOrderDetails(orderId)
                .subscribeOn(mySchedulerProvider.io())
                .doOnSubscribe(onSubscribe -> orderController.setLoadingOrder(true))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(orderDetails ->
                                orderController.setOrderDetails(orderDetails),
                        error ->
                                orderController.setError(true));
    }
}
