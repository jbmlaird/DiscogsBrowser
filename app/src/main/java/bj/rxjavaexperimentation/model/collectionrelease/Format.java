
package bj.rxjavaexperimentation.model.collectionrelease;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Format {

    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("descriptions")
    @Expose
    private List<String> descriptions = null;
    @SerializedName("name")
    @Expose
    private String name;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
