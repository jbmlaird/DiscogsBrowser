package bj.discogsbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Contributor(@SerializedName("resource_url") val resourcUrl: String, val username: String)