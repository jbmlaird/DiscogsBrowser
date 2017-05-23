package bj.vinylbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Submitter(@SerializedName("resource_url") val resourceUrl: String, val username: String)