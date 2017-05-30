package bj.vinylbrowser.model.master

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.common.Artist
import bj.vinylbrowser.model.release.Image
import bj.vinylbrowser.model.release.Track
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Master(var styles: List<String> = emptyList(),
                  var videos: List<Video> = emptyList(),
                  var artists: List<Artist> = emptyList(),
                  @SerializedName("versions_url") var versionsUrl: String = "",
                  var year: String = "",
                  var images: List<Image> = emptyList(),
                  var id: String = "",
                  var tracklist: List<Track> = emptyList(),
                  var genres: List<String> = emptyList(),
                  @SerializedName("num_for_sale") var numForSale: String = "",
                  var title: String = "",
                  @SerializedName("main_release") var mainRelease: Int = 0,
                  @SerializedName("main_release_url") var mainReleaseUrl: String = "",
                  var uri: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "",
                  @SerializedName("lowest_price") var lowestPrice: Double = 0.0,
                  @SerializedName("data_quality") var dataQuality: String = "",
                  var lowestPriceString: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringList(styles)
        dest.writeList(videos)
        dest.writeList(artists)
        dest.writeString(versionsUrl)
        dest.writeString(year)
        dest.writeList(images)
        dest.writeString(id)
        dest.writeList(tracklist)
        dest.writeStringList(genres)
        dest.writeString(numForSale)
        dest.writeString(title)
        dest.writeInt(mainRelease)
        dest.writeString(mainReleaseUrl)
        dest.writeString(uri)
        dest.writeString(resourceUrl)
        dest.writeDouble(lowestPrice)
        dest.writeString(dataQuality)
        dest.writeString(lowestPriceString)
    }

    override fun describeContents(): Int {
        return 0
    }
}