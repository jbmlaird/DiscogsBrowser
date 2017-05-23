package bj.vinylbrowser.order

import bj.vinylbrowser.model.common.Price
import bj.vinylbrowser.model.listing.ListingRelease
import bj.vinylbrowser.model.order.Buyer
import bj.vinylbrowser.model.order.Item
import bj.vinylbrowser.model.order.Order
import bj.vinylbrowser.model.order.Total

/**
 * Created by Josh Laird on 19/05/2017.
 */
object OrderFactory {
    @JvmStatic fun buildOneOrderWithItems(items: Int): Order {
        val order = buildOrder("order1", 120.0, "buyer1")
        order.items = (1..items).map {
            buildItem(it * 20.0, "itemNumber" + it)
        }
        return order
    }

    @JvmStatic fun buildListOfOrders(orders: Int): List<Order> {
        return (1..orders).map { buildOrder("orderId" + it, (it * 20).toDouble(), "buyer" + it) }
    }

    fun buildOrder(id: String, total: Double, buyerName: String): Order {
        val order = Order()
        order.items = listOf(buildItem(total, "description"))
        order.id = id
        order.status = "ok"
        order.buyer = Buyer("", buyerName, 0)
        order.shippingAddress = "UK"
        order.additionalInstructions = "additionalInstructions"
        order.total = Total("", total)
        order.lastActivity = "2008-09-15T15:53:00+05:00"
        return order
    }

    private fun buildItem(price: Double, description: String): Item {
        return Item(buildListingRelease(description), Price("£", price), "id")
    }

    @JvmStatic fun buildListingRelease(description: String): ListingRelease {
        val listingRelease = ListingRelease()
        listingRelease.description = description
        listingRelease.thumbnail = ""
        return listingRelease
    }
}