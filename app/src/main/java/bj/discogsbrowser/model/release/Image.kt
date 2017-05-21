package bj.discogsbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Image(var height: Int = 0,
                 @SerializedName("resource_url") var resourceUrl: String = "",
                 var type: String = "",
                 var uri: String = "",
                 var uri150: String = "",
                 var width: Int = 0)