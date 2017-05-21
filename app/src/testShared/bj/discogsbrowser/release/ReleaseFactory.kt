package bj.discogsbrowser.release

import bj.discogsbrowser.model.common.Artist
import bj.discogsbrowser.model.common.Label
import bj.discogsbrowser.model.release.*

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ReleaseFactory {
    @JvmStatic fun buildReleaseWithLabelSomeForSale(id: String): Release {
        return buildRelease(id, "", true, false, false, 0, 0, 2)
    }

    @JvmStatic fun buildReleaseWithLabelNoneForSale(id: String): Release {
        return buildRelease("", id, true, false, false, 0, 0, 0)
    }

    @JvmStatic fun buildReleaseWithLabelSevenTracksNoVideos(id: String): Release {
        return buildRelease("", id, true, false, false, 7, 0, 0)
    }

    @JvmStatic fun buildReleaseWithLabelFiveTracksTwoVideos(id: String): Release {
        return buildRelease("", id, true, false, false, 5, 2, 0)
    }

    @JvmStatic fun getReleaseNoLabelNoneForSaleNoTracklistNoVideos(id: String): Release {
        return buildRelease("", id, false, false, false, 0, 0, 0)
    }

    @JvmStatic fun buildRelease(id: String, releaseNumber: String, hasLabel: Boolean, isInCollection: Boolean, isInWantlist: Boolean,
                                numberOfTracks: Int, numberOfVideos: Int, numForSale: Int): Release {
        val release = Release()
        release.id = id
        release.title = "releaseTitle" + releaseNumber
        release.tracklist = (1..numberOfTracks).map { buildTrack(it, it.toString()) }
        release.isInCollection = isInCollection
        release.isInWantlist = isInWantlist
        release.videos = (1..numberOfVideos).map { buildVideo(it) }
        if (hasLabel)
            release.labels = listOf(buildReleaseLabel(id))
        release.numForSale = numForSale
        return release
    }

    @JvmStatic fun buildReleaseLabel(labelId: String): Label {
        return Label(id = labelId)
    }

    private fun buildVideo(index: Int): Video {
        return Video("", 0, false, "videoNumber" + index, "uri=uri")
    }

    private fun buildTrack(number: Int, trackNumber: String): Track {
        return Track("", number.toString(), "track" + number, trackNumber)
    }

    private fun buildCommunity(): Community {
        return Community(listOf(Contributor("", "")), "dataQuality", 4, Rating(5.0, 2), "status",
                Submitter("", ""), 5)
    }

    private fun buildArtist(): Artist {
        return Artist("anv", 2, "join", "artistName", "resourceUrl", "role", "tracks")
    }
}