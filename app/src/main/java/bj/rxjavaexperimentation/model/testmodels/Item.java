
package bj.rxjavaexperimentation.model.testmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("release")
    @Expose
    private Release release;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}