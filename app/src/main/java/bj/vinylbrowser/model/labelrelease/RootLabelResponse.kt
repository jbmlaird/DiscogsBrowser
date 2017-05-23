package bj.vinylbrowser.model.labelrelease

import bj.vinylbrowser.model.common.Pagination
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootLabelResponse(val pagination: Pagination,
                             @SerializedName("releases") val labelReleases: List<LabelRelease>)