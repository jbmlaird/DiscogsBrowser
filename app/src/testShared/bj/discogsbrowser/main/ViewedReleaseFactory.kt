package bj.discogsbrowser.main

import bj.discogsbrowser.greendao.ViewedRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ViewedReleaseFactory {
    @JvmStatic fun buildViewedReleases(number: Int): List<ViewedRelease> {
        return (1..number).map { buildViewedRelease(it) }
    }

    private fun buildViewedRelease(number: Int): ViewedRelease {
        val viewedRelease = ViewedRelease()
        viewedRelease.style = "techno"
        viewedRelease.releaseName = "viewedReleaseName" + number
        viewedRelease.artists = "viewedReleaseArtists" + number
        viewedRelease.releaseId = "viewedReleaseId" + number
        return viewedRelease
    }
}