package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Rating(val average: Double, val count: Int) : Parcelable {
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeDouble(average)
        dest.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0
    }
}