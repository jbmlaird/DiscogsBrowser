package bj.discogsbrowser.artistreleases

import bj.discogsbrowser.model.release.Image

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ImageFactory {
    @JvmStatic fun buildImage(): Image {
        return Image()
    }
}