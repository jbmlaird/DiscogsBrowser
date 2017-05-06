
package bj.discogsbrowser.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message
{

    @SerializedName("refund")
    @Expose
    private Refund refund;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("actor")
    @Expose
    private Actor actor;
    @SerializedName("original")
    @Expose
    private Integer original;
    @SerializedName("new")
    @Expose
    private Integer _new;

    public Refund getRefund()
    {
        return refund;
    }

    public void setRefund(Refund refund)
    {
        this.refund = refund;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public From getFrom()
    {
        return from;
    }

    public void setFrom(From from)
    {
        this.from = from;
    }

    public Integer getStatusId()
    {
        return statusId;
    }

    public void setStatusId(Integer statusId)
    {
        this.statusId = statusId;
    }

    public Actor getActor()
    {
        return actor;
    }

    public void setActor(Actor actor)
    {
        this.actor = actor;
    }

    public Integer getOriginal()
    {
        return original;
    }

    public void setOriginal(Integer original)
    {
        this.original = original;
    }

    public Integer getNew()
    {
        return _new;
    }

    public void setNew(Integer _new)
    {
        this._new = _new;
    }

}
