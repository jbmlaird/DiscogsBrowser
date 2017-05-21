package bj.discogsbrowser.search

import bj.discogsbrowser.model.common.Pagination
import bj.discogsbrowser.model.common.Urls
import bj.discogsbrowser.model.search.Community
import bj.discogsbrowser.model.search.RootSearchResponse
import bj.discogsbrowser.model.search.SearchResult

/**
 * Created by Josh Laird on 19/05/2017.
 */
object RootSearchResponseFactory {
    @JvmStatic fun buildRootSearchResponse(): RootSearchResponse {
        val numbers = (0..20).map { buildSearchResult(it) }
        return RootSearchResponse(buildPagination(), numbers)
    }

    private fun buildSearchResult(it: Int): SearchResult {
        return SearchResult("thumb", "searchResult" + it, "uri", "resourceUrl",
                "type", "id", listOf("style"), listOf("format"), "country", listOf("barcode"), Community(2, 2),
                listOf("label"), "catno", "year", listOf("genres"))
    }

    fun buildPagination(): Pagination {
        return Pagination(1, 1, 1, 20, Urls("", ""))
    }
}