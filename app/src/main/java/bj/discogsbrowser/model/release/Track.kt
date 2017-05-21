package bj.discogsbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Track(val duration: String, val position: String, val title: String, @SerializedName("type_") val type: String)