package bj.vinylbrowser.model.master

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Tracklist(val duration: String, val position: String, @SerializedName("type_") val type: String,
                     val title: String)