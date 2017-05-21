package bj.discogsbrowser.model.version

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class MasterVersion(var status: String = "",
                         var thumb: String = "",
                         var format: String = "",
                         var country: String = "",
                         var title: String = "",
                         var label: String = "",
                         var released: String = "",
                         @SerializedName("major_formats") var majorFormats: List<String> = emptyList(),
                         var catno: String = "",
                         @SerializedName("resource_url") var resourceUrl: String = "",
                         var id: String = "")