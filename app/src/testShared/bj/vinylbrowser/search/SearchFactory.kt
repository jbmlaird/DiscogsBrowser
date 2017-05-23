package bj.vinylbrowser.search

import bj.vinylbrowser.greendao.SearchTerm
import bj.vinylbrowser.model.search.SearchResult

/**
 * Created by Josh Laird on 21/05/2017.
 */
object SearchFactory {
    // Java seems to interpret all of these SearchTerms as the same when using .indexOf() so this
    // must be incremented to be recognised as different
    var index: Int = 0

    @JvmStatic fun buildThreeSearchResults(): List<SearchResult> {
        return listOf(buildSearchResult("release"), buildSearchResult("release"),
                buildSearchResult("release"))
    }

    @JvmStatic fun buildArtistAndReleaseSearchResult(): List<SearchResult> {
        return listOf(buildSearchResult("artist"), buildSearchResult("release"))
    }

    @JvmStatic fun buildPastSearch(): SearchTerm {
        val searchTerm = SearchTerm()
        searchTerm.searchTerm = "yeson"
        return searchTerm
    }

    private fun buildSearchResult(type: String): SearchResult {
        val result = SearchResult()
        result.id = index++.toString()
        result.title = "title"
        result.type = type
        result.thumb = "thumb"
        return result
    }
}