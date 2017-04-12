
package bj.rxjavaexperimentation.model.labelrelease;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootLabelResponse {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("releases")
    @Expose
    private List<LabelRelease> labelReleases = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<LabelRelease> getLabelReleases() {
        return labelReleases;
    }

    public void setLabelReleases(List<LabelRelease> labelReleases) {
        this.labelReleases = labelReleases;
    }

}
