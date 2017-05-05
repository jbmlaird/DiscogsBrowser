
package bj.rxjavaexperimentation.model.testmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refund {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("order")
    @Expose
    private Order order;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
