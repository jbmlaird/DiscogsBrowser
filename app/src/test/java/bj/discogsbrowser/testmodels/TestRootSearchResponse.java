package bj.discogsbrowser.testmodels;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.model.common.Pagination;
import bj.discogsbrowser.model.search.RootSearchResponse;
import bj.discogsbrowser.model.search.SearchResult;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class TestRootSearchResponse extends RootSearchResponse
{
    @Override
    public Pagination getPagination()
    {
        return new TestPagination();
    }

    /**
     * Return 20 TestSearchResults.
     *
     * @return 20 TestSearchResults
     */
    @Override
    public List<SearchResult> getSearchResults()
    {
        List<SearchResult> searchResults = new ArrayList<>();
        for (int i = 0; i < 21; i++)
        {
            searchResults.add(new SearchResult());
        }
        return searchResults;
    }

    private class TestPagination extends Pagination
    {
        @Override
        public Integer getPages()
        {
            return 1;
        }
    }
}
