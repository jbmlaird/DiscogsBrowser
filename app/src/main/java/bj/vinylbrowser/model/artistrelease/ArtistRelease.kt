package bj.vinylbrowser.model.artistrelease

import bj.vinylbrowser.model.common.RecyclerViewModel
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class ArtistRelease(var status: String = "",
                         @get:JvmName("getThumb_") var thumb: String = "",
                         @get:JvmName("getTitle_") var title: String = "",
                         var format: String = "",
                         var label: String = "",
                         var role: String = "",
                         var year: String = "",
                         @SerializedName("resource_url") var resourceUrl: String = "",
                         var artist: String = "",
                         @get:JvmName("getType_") var type: String = "",
                         @get:JvmName("getId_") var id: String = "",
                         @SerializedName("main_release") var mainRelease: String = "",
                         var trackInfo: String = "") :
        RecyclerViewModel {

    override fun getTitle(): String {
        return title
    }

    override fun getSubtitle(): String {
        return artist
    }

    override fun getThumb(): String {
        return thumb
    }

    override fun getType(): String {
        return type
    }

    override fun getId(): String {
        return id
    }
}