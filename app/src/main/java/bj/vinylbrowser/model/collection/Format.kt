package bj.vinylbrowser.model.collection

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Format(val qty: String,
                  val descriptions: List<String>,
                  val name: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(qty)
        dest.writeList(descriptions)
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }
}