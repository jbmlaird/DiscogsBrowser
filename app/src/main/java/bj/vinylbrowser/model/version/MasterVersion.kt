package bj.vinylbrowser.model.version

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class MasterVersion(var status: String = "",
                         var thumb: String = "",
                         var format: String = "",
                         var country: String = "",
                         var title: String = "",
                         var label: String = "",
                         var released: String = "",
                         @SerializedName("major_formats") var majorFormats: List<String> = emptyList(),
                         var catno: String = "",
                         @SerializedName("resource_url") var resourceUrl: String = "",
                         var id: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(status)
        dest.writeString(thumb)
        dest.writeString(format)
        dest.writeString(country)
        dest.writeString(title)
        dest.writeString(label)
        dest.writeString(released)
        dest.writeStringList(majorFormats)
        dest.writeString(catno)
        dest.writeString(resourceUrl)
        dest.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }
}