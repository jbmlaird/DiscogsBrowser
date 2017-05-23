package bj.vinylbrowser.artist

import bj.vinylbrowser.model.common.Artist

/**
 * Created by Josh Laird on 19/05/2017.
 */
class ArtistFactory {
    companion object {
        @JvmStatic fun buildArtist(): Artist {
            return Artist(name = "ye son")
        }
    }
}