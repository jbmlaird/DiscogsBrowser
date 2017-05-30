package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.common.Artist
import bj.vinylbrowser.model.common.Label
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Release(var title: String = "",
                   var id: String = "",
                   var artists: List<Artist> = emptyList(),
                   var dataQuality: String = "",
                   var thumb: String = "",
                   var community: Community = Community(),
                   var companies: List<Company> = emptyList(),
                   var country: String = "",
                   @SerializedName("date_added") var dateAdded: String = "",
                   @SerializedName("date_changed") var dateChanged: String = "",
                   @SerializedName("estimated_weight") var estimatedWeight: Int = 0,
                   var extraartists: List<Extraartist> = emptyList(),
                   @SerializedName("format_quantity") var formatQuantity: Int = 0,
                   var formats: List<Format> = emptyList(),
                   var genres: List<String> = emptyList(),
                   var identifiers: List<Identifier> = emptyList(),
                   var images: List<Image> = emptyList(),
                   var labels: List<Label> = emptyList(),
                   @SerializedName("lowest_price") var lowestPrice: Double = 0.0,
                   @SerializedName("master_id") var masterId: Int = 0,
                   @SerializedName("master_url") var masterUrl: String = "",
                   var notes: String = "",
                   @SerializedName("num_for_sale") var numForSale: Int = 0,
                   var released: String = "",
                   @SerializedName("released_formatted") var releasedFormatted: String = "",
                   @SerializedName("resource_url") var resourceUrl: String = "",
                   var series: List<Any> = emptyList(),
                   var status: String = "",
                   var styles: List<String> = emptyList(),
                   var tracklist: List<Track>? = emptyList(),
                   var uri: String = "",
                   var videos: List<Video> = emptyList(),
                   var year: Int = 0,
                   var lowestPriceString: String = "",
                   var isInCollection: Boolean = false,
                   var isInWantlist: Boolean = false,
                   var instanceId: String = "") : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(id)
        dest.writeList(artists)
        dest.writeString(dataQuality)
        dest.writeString(thumb)
        dest.writeParcelable(community, 0)
        dest.writeList(companies)
        dest.writeString(country)
        dest.writeString(dateAdded)
        dest.writeString(dateChanged)
        dest.writeInt(estimatedWeight)
        dest.writeList(extraartists)
        dest.writeInt(formatQuantity)
        dest.writeList(formats)
        dest.writeList(genres)
        dest.writeList(identifiers)
        dest.writeList(images)
        dest.writeList(labels)
        dest.writeDouble(lowestPrice)
        dest.writeInt(masterId)
        dest.writeString(masterUrl)
        dest.writeString(notes)
        dest.writeInt(numForSale)
        dest.writeString(released)
        dest.writeString(releasedFormatted)
        dest.writeString(resourceUrl)
        dest.writeList(series)
        dest.writeString(status)
        dest.writeList(styles)
        dest.writeList(tracklist)
        dest.writeString(uri)
        dest.writeList(videos)
        dest.writeInt(year)
        dest.writeString(lowestPriceString)
        dest.writeInt(if (isInCollection) 1 else 0)
        dest.writeInt(if (isInWantlist) 1 else 0)
        dest.writeString(instanceId)
    }

    override fun describeContents(): Int {
        return 0
    }
}