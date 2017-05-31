package bj.vinylbrowser.model.common

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Seller(var username: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "",
                  var id: String = "",
                  var shipping: String = "",
                  var payment: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(username)
        dest.writeString(resourceUrl)
        dest.writeString(id)
        dest.writeString(shipping)
        dest.writeString(payment)
    }

    override fun describeContents(): Int {
        return 0
    }
}