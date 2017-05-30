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
    private OrderEpxController orderEpxController;

    public OrderPresenter(DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, OrderEpxController orderEpxController)
    {
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.orderEpxController = orderEpxController;
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
                .doOnSubscribe(onSubscribe -> orderEpxController.setLoadingOrder(true))
                .observeOn(mySchedulerProvider.ui())
                .subscribe(orderDetails ->
                                orderEpxController.setOrderDetails(orderDetails),
                        error ->
                                orderEpxController.setError(true));
    }
}
