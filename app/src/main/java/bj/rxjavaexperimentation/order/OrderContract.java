package bj.rxjavaexperimentation.order;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public interface OrderContract
{
    interface View
    {
    }

    interface Presenter
    {
        void fetchOrderDetails(String orderId);
    }
}
