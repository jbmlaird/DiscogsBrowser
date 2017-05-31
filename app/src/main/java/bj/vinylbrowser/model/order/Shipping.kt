package bj.vinylbrowser.model.order

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Shipping(val currency: String, val method: String, val value: Double) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(currency)
        dest.writeString(method)
        dest.writeDouble(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}