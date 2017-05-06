package bj.rxjavaexperimentation.model.order;

import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.listing.Release;
import bj.rxjavaexperimentation.model.listing.Seller;

/**
 * Created by Josh Laird on 02/05/2017.
 */

public class TestListing extends Listing
{
    public String id = "";
    public String title = "";
    public String posted = "";
    public Seller seller = new Seller();
    public Release release = new Release();

    public TestListing()
    {
        seller.setUsername("bj");
        release.setDescription("bj");
    }

    @Override
    public Seller getSeller()
    {
        return seller;
    }

    @Override
    public Release getRelease()
    {
        return release;
    }
}
