package bj.discogsbrowser.model.listing

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class ListingRelease(@SerializedName("catalog_number") var catalogNumber: String = "",
                          var id: String = "",
                          var year: Int = 0,
                          @SerializedName("resource_url") var resourceUrl: String = "",
                          var thumbnail: String = "",
                          var description: String = "")