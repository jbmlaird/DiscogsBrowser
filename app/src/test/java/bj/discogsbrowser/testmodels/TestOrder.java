
package bj.discogsbrowser.testmodels;

import bj.discogsbrowser.model.order.Buyer;
import bj.discogsbrowser.model.order.Order;

public class TestOrder extends Order
{
    private Buyer buyer = new Buyer();

    public TestOrder()
    {
        buyer = new Buyer();
        buyer.setUsername("bj");
    }

    @Override
    public String getThumb()
    {
        return "";
    }

    @Override
    public String getId()
    {
        return "";
    }

    @Override
    public String getTitle()
    {
        return "";
    }

    @Override
    public String getType()
    {
        return "";
    }

    @Override
    public Buyer getBuyer()
    {
        return buyer;
    }
}

