package bj.discogsbrowser.order;

import java.util.Collections;

import bj.discogsbrowser.model.order.Buyer;
import bj.discogsbrowser.model.order.Item;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.order.Price;
import bj.discogsbrowser.model.order.Release;
import bj.discogsbrowser.model.order.Total;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class OrderFactory
{
    public String getOrderId()
    {
        return "123";
    }

    public Order getOneItemOrder()
    {
        Order order = buildOrder();
        order.setItems(Collections.singletonList(getItem()));
        return order;
    }

    public Order getFourItemsOrder()
    {
        Order order = buildOrder();
        Item[] items = {getItem(), getItem(), getItem(), getItem()};
        order.setItems(Arrays.asList(items));
        return order;
    }

    private Order buildOrder()
    {
        Order order = new Order();
        order.setStatus("ok");
        order.setBuyer(getBuyer());
        order.setShippingAddress("UK");
        order.setAdditionalInstructions("additionalInstructions");
        order.setTotal(getTotal());
        return order;
    }

    private Buyer getBuyer()
    {
        Buyer buyer = new Buyer();
        buyer.setUsername("vinyl123");
        return buyer;
    }

    private Item getItem()
    {
        Item item = new Item();
        item.setId(123);
        item.setRelease(getRelease());
        item.setPrice(getPrice());
        return item;
    }

    private Release getRelease()
    {
        Release release = new Release();
        release.setDescription("big tunes");
        return release;
    }

    private Price getPrice()
    {
        Price price = new Price();
        price.setValue(420.0);
        return price;
    }

    private Total getTotal()
    {
        Total total = new Total();
        total.setValue(420.0);
        return total;
    }
}
