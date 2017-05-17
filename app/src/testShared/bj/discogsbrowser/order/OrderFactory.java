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
        Order order = buildOrder("order1", 120, "buyer1");
        order.setItems(Collections.singletonList(getItem(120, "item1")));
        return order;
    }

    public static Order getTwoItemsOrder()
    {
        Order order = buildOrder("order1", 150 + 130, "buyer1");
        order.setItems(Arrays.asList(getItem(150, "item1"), getItem(130, "item2")));
        return order;
    }

    public Order getFourItemsOrder()
    {
        Order order = buildOrder("order1", 480, "buyer1");
        Item[] items = {getItem(120, "item1"), getItem(120, "item2"), getItem(120, "item3"), getItem(120, "item4")};
        order.setItems(Arrays.asList(items));
        return order;
    }

    public static List<Order> getListOfTwo()
    {
        return Arrays.asList(buildOrder("order1", 20, "buyer1"), buildOrder("order2", 25, "buyer2"));
    }

    public static List<Order> getListOfSix()
    {
        return Arrays.asList(buildOrder("order1", 20, "buyer1"), buildOrder("order2", 25, "buyer2"), buildOrder("order3", 30, "buyer3"),
                buildOrder("order4", 35, "buyer4"), buildOrder("order5", 40, "buyer5"), buildOrder("order6", 45, "buyer6"));
    }

    private static Order buildOrder(String id, double total, String buyerName)
    {
        Order order = new Order();
        order.setItems(Collections.singletonList(getItem(120, "item1")));
        order.setId(id);
        order.setStatus("ok");
        order.setBuyer(getBuyer(buyerName));
        order.setShippingAddress("UK");
        order.setAdditionalInstructions("additionalInstructions");
        order.setTotal(buildTotal(total));
        order.setLastActivity("2008-09-15T15:53:00+05:00");
        return order;
    }

    private static Buyer getBuyer(String buyerName)
    {
        Buyer buyer = new Buyer();
        buyer.setUsername(buyerName);
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