package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Video(val description: String,
                 val duration: Int,
                 val embed: Boolean,
                 val title: String,
                 val uri: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(description)
        dest.writeInt(duration)
        dest.writeInt(if (embed) 1 else 0)
        dest.writeString(title)
        dest.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }
}