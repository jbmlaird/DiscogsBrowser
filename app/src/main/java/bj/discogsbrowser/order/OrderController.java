package bj.discogsbrowser.order;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import bj.discogsbrowser.R;
import bj.discogsbrowser.epoxy.common.DividerModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.RetryModel_;
import bj.discogsbrowser.epoxy.order.BuyerModel_;
import bj.discogsbrowser.epoxy.order.OrderReleaseModel_;
import bj.discogsbrowser.epoxy.order.TotalModel_;
import bj.discogsbrowser.model.order.Item;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 18/04/2017.
 */
public class OrderController extends EpoxyController
{
    private Order orderDetails;
    private boolean loadingOrder = true;
    private boolean error = false;
    private Context context;
    private OrderContract.View mView;
    private ImageViewAnimator imageViewAnimator;
    private AnalyticsTracker tracker;

    public OrderController(Context context, OrderContract.View mView, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        this.context = context;
        this.mView = mView;
        this.imageViewAnimator = imageViewAnimator;
        this.tracker = tracker;
    }

    @Override
    protected void buildModels()
    {
        new LoadingModel_()
                .id("order loadingOrder")
                .imageViewAnimator(imageViewAnimator)
                .addIf(loadingOrder && !error, this);

        new RetryModel_()
                .errorString("Unable to load order")
                .id("error model")
                .onClick(v -> mView.retry())
                .addIf(error, this);

        if (!error && orderDetails != null)
        {
            new BuyerModel_()
                    .id("Buyer model")
                    .status(orderDetails.getStatus())
                    .buyer(orderDetails.getBuyer().getUsername())
                    .shippingAddress(orderDetails.getShippingAddress())
                    .specialInstructions(orderDetails.getAdditionalInstructions())
                    .addTo(this);

            new DividerModel_()
                    .id("Divider1")
                    .addTo(this);

            for (Item item : orderDetails.getItems())
            {
                new OrderReleaseModel_()
                        .id("item " + orderDetails.getItems().indexOf(item))
                        .releaseName(item.getRelease().getDescription())
                        .price(item.getPrice().getValue())
                        .addTo(this);
            }

            new DividerModel_()
                    .id("Divider2")
                    .addTo(this);

            new TotalModel_()
                    .id("Total")
                    .total(orderDetails.getTotal().getValue())
                    .addTo(this);
        }
    }

    public void setOrderDetails(Order orderDetails)
    {
        error = false;
        loadingOrder = false;
        this.orderDetails = orderDetails;
        requestModelBuild();
    }

    public void setLoadingOrder(boolean loadingOrder)
    {
        this.loadingOrder = loadingOrder;
        if (loadingOrder)
            error = false;
        requestModelBuild();
    }

    public void setError(boolean isError)
    {
        error = isError;
        if (isError)
        {
            loadingOrder = false;
            tracker.send(context.getString(R.string.order_activity), context.getString(R.string.order_activity), context.getString(R.string.error), "fetching details", 1);
        }
        requestModelBuild();
    }
}
