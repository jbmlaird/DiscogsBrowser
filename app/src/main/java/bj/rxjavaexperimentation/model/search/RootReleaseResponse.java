
package bj.rxjavaexperimentation.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.rxjavaexperimentation.model.common.Pagination;
import bj.rxjavaexperimentation.model.release.Release;

public class RootReleaseResponse
{

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("releases")
    @Expose
    private List<Release> releases = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<Release> getReleases()
    {
        return releases;
    }

    public void setReleases(List<Release> releases)
    {
        this.releases = releases;
    }

}
