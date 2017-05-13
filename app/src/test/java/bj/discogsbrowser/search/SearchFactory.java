package bj.discogsbrowser.search;

import java.util.List;

import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.model.search.SearchResult;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Created by Josh Laird on 13/05/2017.
 */

public class SearchFactory
{
    public SearchTerm getPastSearch()
    {
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setSearchTerm("yeson");
        return searchTerm;
    }

    public List<SearchResult> getThreeEmptySearchResults()
    {
        SearchResult[] searchResults = {buildSearchResult(), buildSearchResult(), buildSearchResult()};
        return Arrays.asList(searchResults);
    }

    public List<SearchResult> getArtistAndReleaseSearchResult()
    {
        SearchResult[] searchResults = {buildArtistSearchResult(), buildReleaseSearchResult()};
        return Arrays.asList(searchResults);
    }


    public SearchResult buildArtistSearchResult()
    {
        SearchResult searchResult = new SearchResult();
        searchResult.setTitle("ye");
        searchResult.setType("artist");
        searchResult.setThumb("yeeee");
        return searchResult;
    }

    public SearchResult buildReleaseSearchResult()
    {
        SearchResult searchResult = new SearchResult();
        searchResult.setTitle("ye");
        searchResult.setType("release");
        searchResult.setThumb("yeeee");
        return searchResult;
    }

    public SearchResult buildSearchResult()
    {
        SearchResult searchResult = new SearchResult();
        searchResult.setTitle("ye");
        searchResult.setType("yeee");
        searchResult.setThumb("yeeee");
        return searchResult;
    }
}
