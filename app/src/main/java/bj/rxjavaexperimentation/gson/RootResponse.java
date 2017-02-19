
package bj.rxjavaexperimentation.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootResponse
{
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<Result> getResults()
    {
        return results;
    }

    public void setResults(List<Result> results)
    {
        this.results = results;
    }

}
