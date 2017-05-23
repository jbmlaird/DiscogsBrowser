package bj.vinylbrowser.model.order

import bj.vinylbrowser.model.common.Price
import bj.vinylbrowser.model.listing.ListingRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Item(val release: ListingRelease, val price: Price, val id: String)