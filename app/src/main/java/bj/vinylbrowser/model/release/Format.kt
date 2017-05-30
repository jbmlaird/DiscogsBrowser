package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Format(val descriptions: List<String>,
                  val name: String,
                  val qty: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringList(descriptions)
        dest.writeString(name)
        dest.writeString(qty)
    }

    override fun describeContents(): Int {
        return 0
    }
}