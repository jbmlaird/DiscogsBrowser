package bj.discogsbrowser.model.search

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class SearchResult(var thumb: String = "",
                        var title: String = "",
                        var uri: String = "",
                        @SerializedName("resource_url") var resourceUrl: String = "",
                        var type: String = "",
                        var id: String = "",
                        var style: List<String> = emptyList(),
                        var format: List<String> = emptyList(),
                        var country: String = "",
                        var barcode: List<String> = emptyList(),
                        var community: Community = Community(0, 0),
                        var label: List<String> = emptyList(),
                        var catno: String = "",
                        var year: String = "",
                        var genre: List<String> = emptyList())