package bj.vinylbrowser.order;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;

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

    /**
     * Fetches {@link bj.vinylbrowser.model.order.Order} details from Discogs.
     *
     * @param orderId Order ID.
     */
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