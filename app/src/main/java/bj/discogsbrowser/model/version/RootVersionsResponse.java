
package bj.discogsbrowser.model.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.discogsbrowser.model.common.Pagination;

public class RootVersionsResponse
{
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("versions")
    @Expose
    private List<Version> versions = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<Version> getVersions()
    {
        return versions;
    }

    public void setVersions(List<Version> versions)
    {
        this.versions = versions;
    }

}
