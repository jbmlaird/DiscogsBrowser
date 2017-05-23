package bj.vinylbrowser.artistreleases

import bj.vinylbrowser.model.release.Image

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ImageFactory {
    @JvmStatic fun buildImage(): Image {
        return Image()
    }
}