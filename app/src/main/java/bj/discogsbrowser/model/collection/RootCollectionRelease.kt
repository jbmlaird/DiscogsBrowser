package bj.discogsbrowser.model.collection

import bj.discogsbrowser.model.common.Pagination
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootCollectionRelease(val pagination: Pagination,
                                 @SerializedName("releases") val collectionReleases: List<CollectionRelease>)