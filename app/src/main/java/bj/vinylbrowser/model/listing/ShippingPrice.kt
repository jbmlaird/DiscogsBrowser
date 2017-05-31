package bj.vinylbrowser.model.listing

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class ShippingPrice(val currency: String,
                         val value: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(currency)
        dest.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}