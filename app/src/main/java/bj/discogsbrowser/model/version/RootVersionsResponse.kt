package bj.discogsbrowser.model.version

import bj.discogsbrowser.model.common.Pagination

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootVersionsResponse(val pagination: Pagination, val masterVersions: List<MasterVersion>)