package bj.vinylbrowser.model.artist

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class ArtistWantedUrl(val url: String,
                           val displayText: String,
                           val hexCode: String,
                           val fontAwesomeString: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(url)
        dest.writeString(displayText)
        dest.writeString(hexCode)
        dest.writeString(fontAwesomeString)
    }

    override fun describeContents(): Int {
        return 0
    }
}