package bj.discogsbrowser.search;

import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.search.SearchResult;

/**
 * Created by Josh Laird on 16/05/2017.
 */

public class SearchResultFactory
{
    public static List<SearchResult> getResults()
    {
        return Arrays.asList(buildSearchResult("artist1", "artistTitle1", "artist"),
                buildSearchResult("master1", "masterTitle1", "master"),
                buildSearchResult("master2", "masterTitle2", "master"),
                buildSearchResult("release1", "releaseTitle1", "release"),
                buildSearchResult("release2", "releaseTitle2", "release"),
                buildSearchResult("release3", "releaseTitle3", "release"));
    }

    public static SearchResult buildSearchResult(String id, String title, String type)
    {
        SearchResult searchResult = new SearchResult();
        searchResult.setId(id);
        searchResult.setTitle(title);
        searchResult.setType(type);
        searchResult.setThumb("ye");
        return searchResult;
    }
}
