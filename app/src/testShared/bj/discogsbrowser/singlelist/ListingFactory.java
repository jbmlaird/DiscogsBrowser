package bj.discogsbrowser.singlelist;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.listing.Listing;

import static bj.discogsbrowser.order.OrderFactory.buildOrderRelease;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class ListingFactory
{
    public static List<Listing> getThreeListings()
    {
        return Arrays.asList(buildListing("listingId1", "listingTitle1"), buildListing("listingId2", "listingTitle2"), buildListing("listingId3", "listingTitle3"));
    }

    private static Listing buildListing(String id, String title)
    {
        Listing listing = new Listing();
        listing.setId(id);
        listing.setRelease(buildOrderRelease(title));
        listing.setPosted("");
        return listing;
    }


}
