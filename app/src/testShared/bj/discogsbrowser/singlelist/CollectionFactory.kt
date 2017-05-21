package bj.discogsbrowser.singlelist

import bj.discogsbrowser.model.collection.CollectionRelease
import bj.discogsbrowser.model.common.BasicInformation

/**
 * Created by Josh Laird on 19/05/2017.
 */
object CollectionFactory {
    @JvmStatic fun getThreeCollectionReleases(): List<CollectionRelease> {
        return (1..3).map { buildCollectionRelease("id" + it, "title" + it) }
    }

    private fun buildCollectionRelease(id: String, title: String): CollectionRelease {
        return CollectionRelease(id = id, basicInformation = BasicInformation(title = title))
    }
}