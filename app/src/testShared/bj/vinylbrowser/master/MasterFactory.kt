package bj.vinylbrowser.master

import bj.vinylbrowser.model.common.Artist
import bj.vinylbrowser.model.master.Master
import bj.vinylbrowser.model.release.Image

/**
 * Created by Josh Laird on 19/05/2017.
 */
object MasterFactory {
    @JvmStatic fun buildMaster(): Master {
        return Master(artists = listOf(Artist(name = "artistName")),
                images = listOf(Image(uri = "imageUri")))
    }
}