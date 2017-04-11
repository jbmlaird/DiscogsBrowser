
package bj.rxjavaexperimentation.model.artistrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("next")
    @Expose
    private String next;

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

}
