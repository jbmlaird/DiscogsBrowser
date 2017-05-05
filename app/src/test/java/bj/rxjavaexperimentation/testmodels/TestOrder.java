
package bj.rxjavaexperimentation.model.testmodels;

public class TestOrder extends Order
{
    private String id = "";
    private String username = "";
    private String status = "";
    private String lastActivity = "";
    private Buyer buyer = new Buyer();

    public TestOrder()
    {
        buyer = new Buyer();
        buyer.setUsername("bj");
    }

    @Override
    public Buyer getBuyer()
    {
        return buyer;
    }
}

