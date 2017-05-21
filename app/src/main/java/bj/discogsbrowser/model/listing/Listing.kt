package bj.discogsbrowser.model.listing

import bj.discogsbrowser.model.common.Price
import bj.discogsbrowser.model.common.RecyclerViewModel
import bj.discogsbrowser.model.common.Seller
import bj.discogsbrowser.utils.DateFormatter
import bj.discogsbrowser.wrappers.DateUtilsWrapper
import bj.discogsbrowser.wrappers.SimpleDateFormatWrapper
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Listing(var status: String = "",
                   var price: Price = Price("0", 0.0),
                   @SerializedName("allow_offers") var allowOffers: Boolean = false,
                   @SerializedName("sleeve_condition") var sleeveCondition: String = "",
                   @get:JvmName("getId_") var id: String = "",
                   var condition: String = "",
                   var posted: String = "",
                   @SerializedName("ships_from") var shipsFrom: String = "",
                   var uri: String = "",
                   var comments: String = "",
                   var seller: Seller = Seller(),
                   var shippingPrice: ShippingPrice = ShippingPrice("", ""),
                   var release: ListingRelease = ListingRelease(),
                   var resourceUrl: String = "",
                   var audio: Boolean = false
) : RecyclerViewModel {
    override fun getTitle(): String {
        return release.description
    }

    override fun getSubtitle(): String {
        val dateFormatter = DateFormatter(DateUtilsWrapper(), SimpleDateFormatWrapper())
        return "Posted: " + dateFormatter.formatIsoDate(posted)
    }

    override fun getThumb(): String {
        return release.thumbnail
    }

    override fun getType(): String {
        return "listing"
    }

    override fun getId(): String {
        return id
    }
}