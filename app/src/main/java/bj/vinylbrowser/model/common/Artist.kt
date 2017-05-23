package bj.vinylbrowser.model.common

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Artist(var anv: String = "",
                  var id: Int = 0,
                  var join: String = "",
                  var name: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "",
                  var role: String = "",
                  var tracks: String = "")