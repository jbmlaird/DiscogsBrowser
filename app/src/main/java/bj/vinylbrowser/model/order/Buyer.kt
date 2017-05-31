package bj.vinylbrowser.model.order

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Buyer(@SerializedName("resource_url") val resourceUrl: String, val username: String,
                 val id: Int) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(resourceUrl)
        dest.writeString(username)
        dest.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }
}