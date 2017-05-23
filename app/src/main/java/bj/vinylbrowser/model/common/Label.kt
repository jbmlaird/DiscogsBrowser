package bj.vinylbrowser.model.common

import bj.vinylbrowser.model.release.Image
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Label(@SerializedName("resource_url") var resourceUrl: String = "",
                 @SerializedName("entity_type") var entityType: String = "",
                 var catno: String = "",
                 var id: String = "",
                 var name: String = "",
                 var profile: String = "",
                 @SerializedName("releases_url") var releaseUrl: String = "",
                 var uri: String = "",
                 var images: List<Image> = emptyList(),
                 @SerializedName("data_quality") var dataQuality: String = "",
                 var thumb: String = "")