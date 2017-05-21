package bj.discogsbrowser.artistreleases

import bj.discogsbrowser.model.artistrelease.ArtistRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ArtistReleasesFactory {
    @JvmStatic fun getTwoMastersTwoReleases(): List<ArtistRelease> {
        return listOf(
                buildArtistRelease("master"), buildArtistRelease("master"),
                buildArtistRelease("release"), buildArtistRelease("release")
        )
    }

    fun buildArtistRelease(type: String): ArtistRelease {
        return ArtistRelease("status", "thumb", "title", "format", "label", "role", "year",
                "resourceUrl", "artist", type, "id", "mainReleaseUrl", "trackInfo")
    }
}