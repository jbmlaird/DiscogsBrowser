package bj.discogsbrowser.marketplace;

import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.listing.Price;
import bj.discogsbrowser.model.listing.Release;
import bj.discogsbrowser.model.listing.Seller;

/**
 * Created by Josh Laird on 09/05/2017.
 */

public class ListingFactory
{

    public Listing getListing()
    {
        Listing listing = new Listing();
        Seller seller = new Seller();
        seller.setUsername("username");
        listing.setComments("");
        listing.setSeller(seller);
        listing.setCondition("goodCondition");
        listing.setSeller(getSeller());
        listing.setUri("uri");
        listing.setPrice(getPrice());
        listing.setRelease(getRelease());
        return listing;
    }

    public Seller getSeller()
    {
        Seller seller = new Seller();
        seller.setShipping("shipping");
        seller.setUsername("username");
        return seller;
    }

    public Price getPrice()
    {
        Price price = new Price();
        price.setValue(1.00);
        return price;
    }

    public Release getRelease()
    {
        Release release = new Release();
        release.setDescription("");
        release.setThumbnail("");
        return release;
    }
}
