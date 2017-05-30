package bj.vinylbrowser.model.common

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Artist(var anv: String = "",
                  var id: Int = 0,
                  var join: String = "",
                  var name: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "",
                  var role: String = "",
                  var tracks: String = "") : Parcelable {
    
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(anv)
        dest.writeInt(id)
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