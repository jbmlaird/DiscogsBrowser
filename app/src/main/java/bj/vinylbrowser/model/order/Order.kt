package bj.vinylbrowser.model.order

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.common.RecyclerViewModel
import bj.vinylbrowser.model.common.Seller
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Order(var status: String = "",
                 var fee: Fee = Fee("", 0.0),
                 var created: String = "",
                 var items: List<Item> = emptyList(),
                 var shipping: Shipping = Shipping("", "", 0.0),
                 @SerializedName("shipping_address") var shippingAddress: String = "",
                 @SerializedName("additional_instructions") var additionalInstructions: String = "",
                 var seller: Seller = Seller(),
                 @SerializedName("last_activity") var lastActivity: String = "",
                 var buyer: Buyer = Buyer("", "", 0),
                 var total: Total = Total("", 0.0),
                 @get:JvmName("getId_") var id: String = "",
                 @SerializedName("resource_url") var resourceUrl: String = "",
                 @SerializedName("messages_url") var messagesUrl: String = "",
                 var uri: String = "",
                 @SerializedName("next_status") var nextStatus: List<String> = emptyList())
    : RecyclerViewModel, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(status)
        dest.writeParcelable(fee, 0)
        dest.writeString(created)
        dest.writeList(items)
        dest.writeParcelable(shipping, 0)
        dest.writeString(shippingAddress)
        dest.writeString(additionalInstructions)
        dest.writeParcelable(seller, 0)
        dest.writeString(lastActivity)
        dest.writeParcelable(buyer, 0)
        dest.writeParcelable(total, 0)
        dest.writeString(id)
        dest.writeString(resourceUrl)
        dest.writeString(messagesUrl)
        dest.writeString(uri)
        dest.writeStringList(nextStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun getTitle(): String {
        return id
    }

    override fun getSubtitle(): String {
        return status
    }

    override fun getThumb(): String {
        return items[0].release.thumbnail
    }

    override fun getType(): String {
        return "order"
    }

    override fun getId(): String {
        return id
    }
}