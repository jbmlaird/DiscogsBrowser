package bj.discogsbrowser.label

import bj.discogsbrowser.model.labelrelease.LabelRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
object LabelFactory {
    @JvmStatic fun buildNumberOfLabelReleases(number: Int): List<LabelRelease> {
        return (1..number).map { buildLabelRelease(it) }
    }

    @JvmStatic fun buildLabelRelease(number: Int): LabelRelease {
        val labelRelease = LabelRelease()
        labelRelease.title = "labelRelease" + number
        labelRelease.artist = "labelArtist" + number
        labelRelease.catno = "CATNO" + number
        labelRelease.id = "labelReleaseId" + number
        return labelRelease
    }
}