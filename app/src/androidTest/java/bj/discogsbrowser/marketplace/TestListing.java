package bj.discogsbrowser.marketplace;

import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.listing.Price;
import bj.discogsbrowser.model.listing.Release;
import bj.discogsbrowser.model.listing.Seller;

/**
 * Created by Josh Laird on 15/05/2017.
 */
public class TestListing extends Listing
{
    public TestListing()
    {
        setCondition("aite");
        setSleeveCondition("less aite");
        setComments("'dis shit aite'- Joy Orbison");
        setUri("http://aiteshit.com");
        setSeller(new TestSeller());
        setPrice(new TestPrice());
        setRelease(new TestRelease());
    }

    private class TestSeller extends Seller
    {
        private TestSeller()
        {
            setUsername("Mr.Aite");
            setShipping("Aite Express");
        }
    }

    private class TestPrice extends Price
    {
        public TestPrice()
        {
            setValue(50.0);
        }
    }

    private class TestRelease extends Release
    {
        public TestRelease()
        {
            setDescription("thissomegoodshit");
        }
    }
}
