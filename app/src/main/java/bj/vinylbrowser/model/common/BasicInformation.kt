package bj.vinylbrowser.model.common

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.collection.Format
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class BasicInformation(var formats: List<Format> = emptyList(),
                            var thumb: String = "",
                            var title: String = "",
                            var labels: List<Label> = emptyList(),
                            var year: String = "",
                            var artists: List<Artist> = emptyList(),
                            @SerializedName("resource_url") var resourceUrl: String = "",
                            var id: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(formats)
        dest.writeString(thumb)
        dest.writeString(title)
        dest.writeList(labels)
        dest.writeString(year)
        dest.writeList(artists)
        dest.writeString(resourceUrl)
        dest.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }
}