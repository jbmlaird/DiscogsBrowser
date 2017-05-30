package bj.vinylbrowser.model.labelrelease

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class LabelRelease(var status: String = "",
                        var thumb: String = "",
                        var title: String = "",
                        var format: String = "",
                        var catno: String = "",
                        var year: String = "",
                        @SerializedName("resource_url") var resourceUrl: String = "",
                        var artist: String = "",
                        var id: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(status)
        dest.writeString(thumb)
        dest.writeString(title)
        dest.writeString(format)
        dest.writeString(catno)
        dest.writeString(year)
        dest.writeString(resourceUrl)
        dest.writeString(artist)
        dest.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }
}