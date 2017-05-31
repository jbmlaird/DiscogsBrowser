package bj.vinylbrowser.model.listing

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.common.Price
import bj.vinylbrowser.model.common.RecyclerViewModel
import bj.vinylbrowser.model.common.Seller
import bj.vinylbrowser.utils.DateFormatter
import bj.vinylbrowser.wrappers.DateUtilsWrapper
import bj.vinylbrowser.wrappers.SimpleDateFormatWrapper
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
                   var audio: Boolean = false) : RecyclerViewModel, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(status)
        dest.writeParcelable(price, 0)
        dest.writeInt(if (allowOffers) 0 else 1)
        dest.writeString(sleeveCondition)
        dest.writeString(id)
        dest.writeString(condition)
        dest.writeString(posted)
        dest.writeString(shipsFrom)
        dest.writeString(uri)
        dest.writeString(comments)
        dest.writeParcelable(seller, 0)
        dest.writeParcelable(shippingPrice, 0)
        dest.writeParcelable(release, 0)
        dest.writeString(resourceUrl)
        dest.writeInt(if (audio) 0 else 1)
    }

    override fun describeContents(): Int {
        return 0
    }

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