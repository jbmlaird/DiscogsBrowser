
package bj.rxjavaexperimentation.model.collectionrelease;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bj.rxjavaexperimentation.model.common.Pagination;

public class RootCollectionRelease
{

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("releases")
    @Expose
    private List<CollectionRelease> collectionReleases = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<CollectionRelease> getCollectionReleases() {
        return collectionReleases;
    }

    public void setCollectionReleases(List<CollectionRelease> collectionReleases) {
        this.collectionReleases = collectionReleases;
    }

}
