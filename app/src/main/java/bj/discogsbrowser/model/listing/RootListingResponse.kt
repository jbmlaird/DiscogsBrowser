package bj.discogsbrowser.model.listing

import bj.discogsbrowser.model.common.Pagination

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootListingResponse(val pagination: Pagination, val listings: List<Listing>)