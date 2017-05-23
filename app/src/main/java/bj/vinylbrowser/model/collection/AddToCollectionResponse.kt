package bj.vinylbrowser.model.collection

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class AddToCollectionResponse(@SerializedName("instance_id") var instanceId: String = "",
                                   @SerializedName("resource_url") var resouceUrl: String = "")