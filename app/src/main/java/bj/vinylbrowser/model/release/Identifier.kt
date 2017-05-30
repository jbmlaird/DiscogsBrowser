package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Identifier(val type: String,
                      val value: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(type)
        dest.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}