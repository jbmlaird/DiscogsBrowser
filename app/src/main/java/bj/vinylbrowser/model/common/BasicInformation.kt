package bj.vinylbrowser.model.common

import bj.vinylbrowser.model.collection.Format
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class BasicInformation(var formats: List<Format> = emptyList(),
                            var thumb: String = "",
                            var title: String = "",
                            var labels: List<Label> = emptyList(),
                            var year: String = "",
                            var artists: List<Artist> = emptyList(),
                            @SerializedName("resource_url") var resourceUrl: String = "",
                            var id: String = "")