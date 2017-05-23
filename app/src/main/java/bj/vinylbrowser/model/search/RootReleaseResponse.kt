package bj.vinylbrowser.model.search

import bj.vinylbrowser.model.common.Pagination
import bj.vinylbrowser.model.release.Release

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootReleaseResponse(val pagination: Pagination, val releases: List<Release>)