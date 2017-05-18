package bj.discogsbrowser.model.artist

import bj.discogsbrowser.model.release.Image
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 18/05/2017.
 */
data class ArtistResult(@SerializedName("namevariations") val nameVariations: List<String>,
                        @SerializedName("profile") var profile: String,
                        @SerializedName("releases_url") val releasesUrl: String?,
                        @SerializedName("uri") val uri: String,
                        @SerializedName("urls") val urls: List<String>,
                        @SerializedName("data_quality") val dataQuality: String,
                        @SerializedName("id") val id: String,
                        @SerializedName("images") val images: List<Image>?,
                        @SerializedName("members") val members: List<Member>,
                        var artistWantedUrls: List<ArtistWantedUrl>?)