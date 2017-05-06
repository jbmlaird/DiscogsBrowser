package bj.rxjavaexperimentation.order;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.epoxy.common.DividerModel_;
import bj.rxjavaexperimentation.epoxy.common.ErrorModel_;
import bj.rxjavaexperimentation.epoxy.common.LoadingModel_;
import bj.rxjavaexperimentation.epoxy.order.BuyerModel_;
import bj.rxjavaexperimentation.epoxy.order.OrderReleaseModel_;
import bj.rxjavaexperimentation.epoxy.order.TotalModel_;
import bj.rxjavaexperimentation.model.order.Item;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.utils.AnalyticsTracker;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@Singleton
public class OrderController extends EpoxyController
{
    private Order orderDetails;
    private boolean loadingOrder = true;
    private boolean error = false;
    private Context context;
    private OrderContract.View mView;
    private ImageViewAnimator imageViewAnimator;
    private AnalyticsTracker tracker;

    @Inject
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

        new ErrorModel_()
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
                        .id("item " + item.getId().toString())
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

    public void errorFetchingDetails()
    {
        error = true;
        loadingOrder = false;
        tracker.send(context.getString(R.string.order_activity), context.getString(R.string.order_activity), context.getString(R.string.error), "fetching details", 1L);
        requestModelBuild();
    }
}
