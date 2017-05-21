package bj.discogsbrowser.model.common

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Seller(var username: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "",
                  var id: String = "",
                  var shipping: String = "",
                  var payment: String = "")