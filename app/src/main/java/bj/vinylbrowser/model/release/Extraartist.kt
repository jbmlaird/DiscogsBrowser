package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Extraartist(val anv: String,
                       val id: String,
                       val join: String,
                       val name: String,
                       @SerializedName("resource_url") val resourceUrl: String,
                       val role: String,
                       val tracks: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(anv)
        dest.writeString(id)
        dest.writeString(join)
        dest.writeString(name)
        dest.writeString(resourceUrl)
        dest.writeString(role)
        dest.writeString(tracks)
    }

    override fun describeContents(): Int {
        return 0
    }
}