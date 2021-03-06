package bj.vinylbrowser.model.common

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Price(val currency: String,
                 val value: Double) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(currency)
        dest.writeDouble(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}