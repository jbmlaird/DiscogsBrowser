package bj.discogsbrowser.model.labelrelease

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class LabelRelease(var status: String = "",
                        var thumb: String = "",
                        var title: String = "",
                        var format: String = "",
                        var catno: String = "",
                        var year: String = "",
                        @SerializedName("resource_url") var resourceUrl: String = "",
                        var artist: String = "",
                        var id: String = "")