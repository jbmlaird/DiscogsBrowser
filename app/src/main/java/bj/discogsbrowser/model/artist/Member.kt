package bj.discogsbrowser.model.artist

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Member(var active: String = "",
                  var id: String = "",
                  var url: String = "",
                  var name: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "")