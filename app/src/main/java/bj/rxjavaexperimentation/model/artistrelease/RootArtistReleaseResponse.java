
package bj.rxjavaexperimentation.model.artistrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.rxjavaexperimentation.model.common.Pagination;

public class RootArtistReleaseResponse
{
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("releases")
    @Expose
    private List<ArtistRelease> artistReleases = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<ArtistRelease> getArtistReleases()
    {
        return artistReleases;
    }

    public void setArtistReleases(List<ArtistRelease> artistReleases)
    {
        this.artistReleases = artistReleases;
    }

}
