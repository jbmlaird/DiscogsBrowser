package bj.discogsbrowser.model.search

import bj.discogsbrowser.model.common.Pagination
import bj.discogsbrowser.model.release.Release

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootReleaseResponse(val pagination: Pagination, val releases: List<Release>)