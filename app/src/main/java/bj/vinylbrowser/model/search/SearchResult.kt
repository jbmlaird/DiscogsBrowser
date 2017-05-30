package bj.vinylbrowser.model.search

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class SearchResult(var thumb: String = "",
                        var title: String = "",
                        var uri: String = "",
                        @SerializedName("resource_url") var resourceUrl: String = "",
                        var type: String = "",
                        var id: String = "",
                        var style: List<String> = emptyList(),
                        var format: List<String> = emptyList(),
                        var country: String = "",
                        var barcode: List<String> = emptyList(),
                        var community: Community = Community(0, 0),
                        var label: List<String> = emptyList(),
                        var catno: String = "",
                        var year: String = "",
                        var genre: List<String> = emptyList()) : Parcelable {

    private constructor(parcel: Parcel) : this(parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.createStringArrayList(), parcel.createStringArrayList(),
            parcel.readString(), parcel.createStringArrayList(), Community(0, 0),
            parcel.createStringArrayList(), parcel.readString(), parcel.readString(),
            parcel.createStringArrayList())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(thumb)
        dest.writeString(title)
        dest.writeString(uri)
        dest.writeString(resourceUrl)
        dest.writeString(type)
        dest.writeString(id)
        dest.writeStringList(style)
        dest.writeStringList(format)
        dest.writeString(country)
        dest.writeStringList(barcode)
        dest.writeStringList(label)
        dest.writeString(catno)
        dest.writeString(year)
        dest.writeStringList(genre)
    }

    override fun describeContents(): Int {
        return 0
    }
}