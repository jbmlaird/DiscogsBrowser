package bj.vinylbrowser.artistreleases

import bj.vinylbrowser.model.artistrelease.ArtistRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ArtistReleasesFactory {
    @JvmStatic fun getTwoMastersTwoReleases(): List<ArtistRelease> {
        return listOf(
                buildArtistRelease("master1", "master"), buildArtistRelease("master2", "master"),
                buildArtistRelease("release1", "release"), buildArtistRelease("release2", "release")
        )
    }

    fun buildArtistRelease(title: String, type: String): ArtistRelease {
        return ArtistRelease("status", "thumb", title, "format", "label", "role", "year",
                "resourceUrl", "artist", type, "id", "mainReleaseUrl", "trackInfo")
    }
}