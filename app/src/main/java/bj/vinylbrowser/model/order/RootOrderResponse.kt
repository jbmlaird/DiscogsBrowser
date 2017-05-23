package bj.vinylbrowser.model.order

import bj.vinylbrowser.model.common.Pagination

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class RootOrderResponse(val pagination: Pagination, val orders: List<Order>)