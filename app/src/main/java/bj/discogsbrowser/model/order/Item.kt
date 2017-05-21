package bj.discogsbrowser.model.order

import bj.discogsbrowser.model.common.Price
import bj.discogsbrowser.model.listing.ListingRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Item(val release: ListingRelease, val price: Price, val id: String)