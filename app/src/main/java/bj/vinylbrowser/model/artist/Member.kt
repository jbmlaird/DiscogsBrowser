package bj.vinylbrowser.model.artist

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Member(var active: String = "",
                  var id: String = "",
                  var url: String = "",
                  var name: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(active)
        dest.writeString(id)
        dest.writeString(url)
        dest.writeString(name)
        dest.writeString(resourceUrl)
    }

    override fun describeContents(): Int {
        return 0
    }
}