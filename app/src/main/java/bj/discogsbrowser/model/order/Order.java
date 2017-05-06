
package bj.discogsbrowser.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.discogsbrowser.model.common.RecyclerViewModel;

public class Order implements RecyclerViewModel
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fee")
    @Expose
    private Fee fee;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("shipping_address")
    @Expose
    private String shippingAddress;
    @SerializedName("additional_instructions")
    @Expose
    private String additionalInstructions;
    @SerializedName("seller")
    @Expose
    private Seller seller;
    @SerializedName("last_activity")
    @Expose
    private String lastActivity;
    @SerializedName("buyer")
    @Expose
    private Buyer buyer;
    @SerializedName("total")
    @Expose
    private Total total;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("messages_url")
    @Expose
    private String messagesUrl;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("next_status")
    @Expose
    private List<String> nextStatus = null;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Fee getFee()
    {
        return fee;
    }

    public void setFee(Fee fee)
    {
        this.fee = fee;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    public Shipping getShipping()
    {
        return shipping;
    }

    public void setShipping(Shipping shipping)
    {
        this.shipping = shipping;
    }

    public String getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public String getAdditionalInstructions()
    {
        return additionalInstructions;
    }

    public void setAdditionalInstructions(String additionalInstructions)
    {
        this.additionalInstructions = additionalInstructions;
    }

    public Seller getSeller()
    {
        return seller;
    }

    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }

    public String getLastActivity()
    {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity)
    {
        this.lastActivity = lastActivity;
    }

    public Buyer getBuyer()
    {
        return buyer;
    }

    public void setBuyer(Buyer buyer)
    {
        this.buyer = buyer;
    }

    public Total getTotal()
    {
        return total;
    }

    public void setTotal(Total total)
    {
        this.total = total;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public String getMessagesUrl()
    {
        return messagesUrl;
    }

    public void setMessagesUrl(String messagesUrl)
    {
        this.messagesUrl = messagesUrl;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public List<String> getNextStatus()
    {
        return nextStatus;
    }

    public void setNextStatus(List<String> nextStatus)
    {
        this.nextStatus = nextStatus;
    }

    @Override
    public String getTitle()
    {
        return id;
    }

    @Override
    public String getSubtitle()
    {
        return status;
    }

    @Override
    public String getThumb()
    {
        return items.get(0).getRelease().getThumbnail();
    }

    @Override
    public String getType()
    {
        return "order";
    }
}
