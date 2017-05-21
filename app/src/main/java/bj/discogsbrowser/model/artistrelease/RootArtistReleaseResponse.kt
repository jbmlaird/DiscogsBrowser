package bj.discogsbrowser.model.artistrelease

import bj.discogsbrowser.model.common.Pagination
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootArtistReleaseResponse(val pagination: Pagination,
                                     @SerializedName("releases") val artistReleases: List<ArtistRelease>)