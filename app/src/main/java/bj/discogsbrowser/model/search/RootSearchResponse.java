
package bj.discogsbrowser.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.discogsbrowser.model.common.Pagination;

public class RootSearchResponse
{
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("results")
    @Expose
    private List<SearchResult> searchResults = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<SearchResult> getSearchResults()
    {
        return searchResults;
    }
}
