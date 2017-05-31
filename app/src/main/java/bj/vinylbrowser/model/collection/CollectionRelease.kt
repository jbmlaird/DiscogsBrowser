package bj.vinylbrowser.model.collection

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.common.BasicInformation
import bj.vinylbrowser.model.common.RecyclerViewModel
import bj.vinylbrowser.utils.ArtistsBeautifier
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class CollectionRelease(@get:JvmName("getId_") var id: String = "",
                             @SerializedName("instance_id") var instanceId: String = "",
                             @SerializedName("folder_id") var folderId: String = "",
                             var rating: Int = 0,
                             @SerializedName("basic_information") var basicInformation: BasicInformation = BasicInformation(),
                             var notes: List<Note> = emptyList(),
                             @get:JvmName("getSubtitle_") var subtitle: String = "")
    : RecyclerViewModel, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(instanceId)
        dest.writeString(folderId)
        dest.writeInt(rating)
        dest.writeParcelable(basicInformation, 0)
        dest.writeList(notes)
        dest.writeString(subtitle)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun getTitle(): String {
        return basicInformation.title
    }

    override fun getSubtitle(): String {
        if (subtitle == "") {
            val artistsBeautifier = ArtistsBeautifier()
            subtitle = artistsBeautifier.formatArtists(basicInformation.artists)
        }
        return subtitle
    }

    override fun getThumb(): String {
        return basicInformation.thumb
    }

    override fun getType(): String {
        return "release"
    }

    override fun getId(): String {
        return id
    }
}