package bj.discogsbrowser.order;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.listing.Release;
import bj.discogsbrowser.model.order.Buyer;
import bj.discogsbrowser.model.order.Item;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.order.Price;
import bj.discogsbrowser.model.order.Total;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class OrderFactory
{
    public String getOrderId()
    {
        return "123";
    }

    public static Order getOneItemOrder()
    {
        Order order = buildOrder("order1", 120);
        order.setItems(Collections.singletonList(getItem(120, "item1")));
        return order;
    }

    public static Order getTwoItemsOrder()
    {
        Order order = buildOrder("order1", 150 + 130);
        order.setItems(Arrays.asList(getItem(150, "item1"), getItem(130, "item2")));
        return order;
    }

    public Order getFourItemsOrder()
    {
        Order order = buildOrder("order1", 480);
        Item[] items = {getItem(120, "item1"), getItem(120, "item2"), getItem(120, "item3"), getItem(120, "item4")};
        order.setItems(Arrays.asList(items));
        return order;
    }

    public static List<Order> getListOfTwo()
    {
        return Arrays.asList(buildOrder("order1", 20), buildOrder("order2", 25));
    }

    public static List<Order> getListOfSix()
    {
        return Arrays.asList(buildOrder("order1", 20), buildOrder("order2", 25), buildOrder("order3", 30), buildOrder("order4", 35), buildOrder("order5", 40), buildOrder("order6", 45));
    }

    private static Order buildOrder(String id, double total)
    {
        Order order = new Order();
        order.setItems(Collections.singletonList(getItem(120, "item1")));
        order.setId(id);
        order.setStatus("ok");
        order.setBuyer(getBuyer());
        order.setShippingAddress("UK");
        order.setAdditionalInstructions("additionalInstructions");
        order.setTotal(buildTotal(total));
        return order;
    }

    private static Buyer getBuyer()
    {
        Buyer buyer = new Buyer();
        buyer.setUsername("vinyl123");
        return buyer;
    }

    private static Item getItem(double price, String description)
    {
        Item item = new Item();
        item.setId(123);
        item.setRelease(buildOrderRelease(description));
        item.setPrice(getPrice(price));
        return item;
    }

    public static Release buildOrderRelease(String description)
    {
        Release release = new Release();
        release.setDescription(description);
        release.setThumbnail("");
        return release;
    }

    private static Price getPrice(double value)
    {
        Price price = new Price();
        price.setValue(value);
        return price;
    }

    private static Total buildTotal(double orderTotal)
    {
        Total total = new Total();
        total.setValue(orderTotal);
        return total;
    }
}