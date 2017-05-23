package bj.vinylbrowser.model.version

import bj.vinylbrowser.model.common.Pagination
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootVersionsResponse(val pagination: Pagination,
                                @SerializedName("versions") val masterVersions: List<MasterVersion>)