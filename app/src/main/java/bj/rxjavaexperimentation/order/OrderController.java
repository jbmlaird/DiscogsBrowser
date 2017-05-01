package bj.rxjavaexperimentation.order;

import com.airbnb.epoxy.EpoxyController;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.epoxy.common.DividerModel_;
import bj.rxjavaexperimentation.epoxy.common.ErrorModel_;
import bj.rxjavaexperimentation.epoxy.common.LoadingModel_;
import bj.rxjavaexperimentation.epoxy.order.BuyerModel_;
import bj.rxjavaexperimentation.epoxy.order.OrderReleaseModel_;
import bj.rxjavaexperimentation.epoxy.order.TotalModel_;
import bj.rxjavaexperimentation.model.order.Item;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@Singleton
public class OrderController extends EpoxyController
{
    private Order orderDetails;
    private boolean loading = true;
    private boolean error = false;
    private ImageViewAnimator imageViewAnimator;

    @Inject
    public OrderController(ImageViewAnimator imageViewAnimator)
    {
        this.imageViewAnimator = imageViewAnimator;
    }

    @Override
    protected void buildModels()
    {
        new LoadingModel_()
                .id("order loading")
                .imageViewAnimator(imageViewAnimator)
                .addIf(loading, this);

        new ErrorModel_()
                .errorString("Unable to load order")
                .id("error model")
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
        loading = false;
        this.orderDetails = orderDetails;
        requestModelBuild();
    }

    public void errorFetchingDetails()
    {
        error = true;
        requestModelBuild();
    }
}