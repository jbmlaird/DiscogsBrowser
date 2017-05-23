package bj.vinylbrowser.model.label

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class SubLabel(@SerializedName("resource_url") val resourceUrl: String, val id: String, val name: String)