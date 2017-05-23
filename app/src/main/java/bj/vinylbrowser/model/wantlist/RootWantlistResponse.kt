package bj.vinylbrowser.model.wantlist

import bj.vinylbrowser.model.common.Pagination

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootWantlistResponse(val pagination: Pagination, val wants: List<Want>)