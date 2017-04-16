
package bj.rxjavaexperimentation.model.wantlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootWantlistResponse {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("wants")
    @Expose
    private List<Want> wants = null;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Want> getWants() {
        return wants;
    }

    public void setWants(List<Want> wants) {
        this.wants = wants;
    }

}
