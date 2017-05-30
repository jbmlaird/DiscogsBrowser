package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Track(val duration: String,
                 val position: String,
                 val title: String,
                 @SerializedName("type_") val type: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(duration)
        dest.writeString(position)
        dest.writeString(title)
        dest.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }
}