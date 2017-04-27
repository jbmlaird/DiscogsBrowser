package bj.rxjavaexperimentation.epoxy.order;

import com.airbnb.epoxy.EpoxyController;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.epoxy.common.DividerModel_;
import bj.rxjavaexperimentation.model.order.Item;
import bj.rxjavaexperimentation.model.order.Order;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@Singleton
public class OrderController extends EpoxyController
{
    private Order orderDetails = new Order();
    private boolean error = false;

    @Inject
    public OrderController()
    {
    }

    @Override
    protected void buildModels()
    {
        new BuyerModel_()
                .id("Buyer model")
                .status(orderDetails.getStatus())
                .buyer(orderDetails.getBuyer().getUsername())
                .shippingAddress(orderDetails.getShippingAddress())
                .specialInstructions(orderDetails.getAdditionalInstructions())
                .addIf(!error, this);

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

    public void setOrderDetails(Order orderDetails)
    {
        error = false;
        this.orderDetails = orderDetails;
        requestModelBuild();
    }

    public void errorFetchingDetails()
    {
        error = true;
        requestModelBuild();
    }
}
