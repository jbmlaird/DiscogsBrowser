package bj.discogsbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Extraartist(val anv: String, val id: String, val join: String, val name: String,
                       @SerializedName("resource_url") val resourceUrl: String, val role: String,
                       val tracks: String)