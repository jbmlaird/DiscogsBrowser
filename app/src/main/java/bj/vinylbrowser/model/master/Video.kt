package bj.vinylbrowser.model.master

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Video(val duration: Int, val description: String, val embed: Boolean,
                 val uri: String, val title: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(duration)
        dest.writeString(description)
        dest.writeInt(if (embed) 1 else 0)
        dest.writeString(uri)
        dest.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }
}