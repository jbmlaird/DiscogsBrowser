package bj.vinylbrowser.singlelist

import bj.vinylbrowser.model.collection.CollectionRelease
import bj.vinylbrowser.model.common.Artist
import bj.vinylbrowser.model.common.BasicInformation

/**
 * Created by Josh Laird on 19/05/2017.
 */
object CollectionFactory {
    @JvmStatic fun getThreeCollectionReleases(): List<CollectionRelease> {
        return (1..3).map { buildCollectionRelease("id" + it, "title" + it) }
    }

    private fun buildCollectionRelease(id: String, title: String): CollectionRelease {
        return CollectionRelease(id = id, basicInformation = BasicInformation(title = title, artists = listOf(Artist(name = "artist" + id))))
    }
}