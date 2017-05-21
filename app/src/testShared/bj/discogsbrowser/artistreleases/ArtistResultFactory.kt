package bj.discogsbrowser.artistreleases

import bj.discogsbrowser.artistreleases.ImageFactory.buildImage
import bj.discogsbrowser.artistreleases.MemberFactory.buildMember
import bj.discogsbrowser.model.artist.ArtistResult
import bj.discogsbrowser.model.artist.ArtistWantedUrl

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ArtistResultFactory {
    @JvmStatic fun buildArtistResult(members: Int): ArtistResult {
        val artistResult = ArtistResult()
        artistResult.nameVariations = listOf("ye")
        artistResult.profile = "artistProfile"
        artistResult.releasesUrl = "http://releaseUrl"
        artistResult.uri = "http://uri"
        artistResult.urls = listOf("http://www.spotify.com", "http://www.redtube.com")
        artistResult.dataQuality = "really good quality, we have the best qualities"
        artistResult.id = "artistResultId"
        artistResult.images = listOf(buildImage(), buildImage())
        artistResult.members = (1..members).map { buildMember(it) }
        artistResult.artistWantedUrls = listOf(ArtistWantedUrl("http://www.spotify.com", "spotify", "#000000", "i"),
                ArtistWantedUrl("http://www.redtube.com", "redtube", "#ffffff", "i"))
        return artistResult
    }
}