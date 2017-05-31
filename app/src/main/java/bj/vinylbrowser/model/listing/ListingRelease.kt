package bj.vinylbrowser.model.listing

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class ListingRelease(@SerializedName("catalog_number") var catalogNumber: String = "",
                          var id: String = "",
                          var year: Int = 0,
                          @SerializedName("resource_url") var resourceUrl: String = "",
                          var thumbnail: String = "",
                          var description: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(catalogNumber)
        dest.writeString(id)
        dest.writeInt(year)
        dest.writeString(resourceUrl)
        dest.writeString(thumbnail)
        dest.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }
}