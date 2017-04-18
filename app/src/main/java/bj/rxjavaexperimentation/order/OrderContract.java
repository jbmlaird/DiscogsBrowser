package bj.rxjavaexperimentation.order;

import bj.rxjavaexperimentation.model.order.Order;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public interface OrderContract
{
    interface View
    {
        void displayOrder(Order orderDetails);

        void displayError();

        void hideLoading();
    }

    interface Presenter
    {
        void fetchOrderDetails(String orderId);
    }
}
