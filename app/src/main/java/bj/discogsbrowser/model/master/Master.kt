package bj.discogsbrowser.model.master

import bj.discogsbrowser.model.common.Artist
import bj.discogsbrowser.model.release.Image
import bj.discogsbrowser.model.release.Track
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Master(var styles: List<String> = emptyList(),
                  var videos: List<Video> = emptyList(),
                  var artists: List<Artist> = emptyList(),
                  @SerializedName("versions_url") var versionsUrl: String = "",
                  var year: String = "",
                  var images: List<Image> = emptyList(),
                  var id: String = "",
                  var tracklist: List<Track> = emptyList(),
                  var genres: List<String> = emptyList(),
                  @SerializedName("num_for_sale") var numForSale: String = "",
                  var title: String = "",
                  @SerializedName("main_release") var mainRelease: Int = 0,
                  @SerializedName("main_release_url") var mainReleaseUrl: String = "",
                  var uri: String = "",
                  @SerializedName("resource_url") var resourceUrl: String = "",
                  @SerializedName("lowest_price") var lowestPrice: Double = 0.0,
                  @SerializedName("data_quality") var dataQuality: String = "",
                  var lowestPriceString: String = "")