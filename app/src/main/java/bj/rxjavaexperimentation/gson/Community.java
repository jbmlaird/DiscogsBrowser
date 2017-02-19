
package bj.rxjavaexperimentation.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Community {

    @SerializedName("have")
    @Expose
    private Integer have;
    @SerializedName("want")
    @Expose
    private Integer want;

    public Integer getHave() {
        return have;
    }

    public void setHave(Integer have) {
        this.have = have;
    }

    public Integer getWant() {
        return want;
    }

    public void setWant(Integer want) {
        this.want = want;
    }

}
