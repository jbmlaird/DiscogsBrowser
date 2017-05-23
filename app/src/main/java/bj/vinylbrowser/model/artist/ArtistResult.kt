package bj.vinylbrowser.model.artist

import bj.vinylbrowser.model.release.Image
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 18/05/2017.
 */
data class ArtistResult(@SerializedName("namevariations") var nameVariations: List<String> = emptyList(),
                        @SerializedName("profile") var profile: String = "",
                        @SerializedName("releases_url") var releasesUrl: String = "",
                        @SerializedName("uri") var uri: String = "",
                        @SerializedName("urls") var urls: List<String> = emptyList(),
                        @SerializedName("data_quality") var dataQuality: String = "",
                        @SerializedName("id") var id: String = "",
                        @SerializedName("images") var images: List<Image> = emptyList(),
                        @SerializedName("members") var members: List<Member> = emptyList(),
                        var artistWantedUrls: List<ArtistWantedUrl> = emptyList())