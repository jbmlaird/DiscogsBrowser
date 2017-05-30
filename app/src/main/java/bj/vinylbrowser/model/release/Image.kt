package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Image(var height: Int = 0,
                 @SerializedName("resource_url") var resourceUrl: String = "",
                 var type: String = "",
                 var uri: String = "",
                 var uri150: String = "",
                 var width: Int = 0) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(height)
        dest.writeString(resourceUrl)
        dest.writeString(type)
        dest.writeString(uri)
        dest.writeString(uri150)
        dest.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }
}