package bj.vinylbrowser.listing

import bj.vinylbrowser.model.common.Price
import bj.vinylbrowser.model.common.Seller
import bj.vinylbrowser.model.listing.Listing
import bj.vinylbrowser.order.OrderFactory.buildListingRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ListingFactory {
    @JvmStatic fun buildNumberOfListings(number: Int): List<Listing> {
        return (1..number).map { buildListing(it.toString()) }
    }

    @JvmStatic fun buildListing(id: String): Listing {
        val listing = Listing()
        listing.id = id
        listing.condition = "aite"
        listing.sleeveCondition = "less aite"
        listing.comments = "'dis shit aite'- Joy Orbison"
        listing.uri = "http://aiteshit.com"
        listing.release = buildListingRelease("description" + id)
        listing.seller = buildSeller()
        listing.price = Price("", 50.0)
        return listing
    }

    @JvmStatic fun buildSeller(): Seller {
        val seller = Seller()
        seller.username = "that's wassup"
        seller.shipping = "AiteExpress"
        return seller
    }
}