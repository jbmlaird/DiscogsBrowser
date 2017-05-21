package bj.discogsbrowser.master

import bj.discogsbrowser.model.common.Artist
import bj.discogsbrowser.model.master.Master
import bj.discogsbrowser.model.release.Image

/**
 * Created by Josh Laird on 19/05/2017.
 */
object MasterFactory {
    @JvmStatic fun buildMaster(): Master {
        return Master(artists = listOf(Artist(name = "artistName")),
                images = listOf(Image(uri = "imageUri")))
    }
}