package bj.discogsbrowser.model.wantlist

import bj.discogsbrowser.model.common.BasicInformation
import bj.discogsbrowser.model.common.RecyclerViewModel
import bj.discogsbrowser.utils.ArtistsBeautifier
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Want(var rating: Int = 0,
                @SerializedName("basic_information") var basicInformation: BasicInformation = BasicInformation(),
                @SerializedName("resource_url") var resourceUrl: String = "",
                @get:JvmName("getId_") var id: String = "",
                var notes: String = "",
                @get:JvmName("getSubtitle_") var subtitle: String = ""
) : RecyclerViewModel {

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