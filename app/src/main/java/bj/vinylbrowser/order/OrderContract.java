package bj.vinylbrowser.order;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public interface OrderContract
{
    interface View
    {
        void retry();
    }

    interface Presenter
    {
        void fetchOrderDetails(String orderId);
    }
}
