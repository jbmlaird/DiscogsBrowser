
package bj.rxjavaexperimentation.model.testmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
