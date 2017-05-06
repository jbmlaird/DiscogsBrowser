
package bj.discogsbrowser.model.listing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.utils.DateFormatter;
import bj.discogsbrowser.wrappers.DateUtilsWrapper;
import bj.discogsbrowser.wrappers.SimpleDateFormatWrapper;

public class Listing implements RecyclerViewModel
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("allow_offers")
    @Expose
    private Boolean allowOffers;
    @SerializedName("sleeve_condition")
    @Expose
    private String sleeveCondition;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("posted")
    @Expose
    private String posted;
    @SerializedName("ships_from")
    @Expose
    private String shipsFrom;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("seller")
    @Expose
    private Seller seller;
    @SerializedName("shipping_price")
    @Expose
    private ShippingPrice shippingPrice;
    @SerializedName("release")
    @Expose
    private Release release;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("audio")
    @Expose
    private Boolean audio;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Price getPrice()
    {
        return price;
    }

    public void setPrice(Price price)
    {
        this.price = price;
    }

    public Boolean getAllowOffers()
    {
        return allowOffers;
    }

    public void setAllowOffers(Boolean allowOffers)
    {
        this.allowOffers = allowOffers;
    }

    public String getSleeveCondition()
    {
        return sleeveCondition;
    }

    public void setSleeveCondition(String sleeveCondition)
    {
        this.sleeveCondition = sleeveCondition;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String getPosted()
    {
        return posted;
    }

    public void setPosted(String posted)
    {
        this.posted = posted;
    }

    public String getShipsFrom()
    {
        return shipsFrom;
    }

    public void setShipsFrom(String shipsFrom)
    {
        this.shipsFrom = shipsFrom;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public Seller getSeller()
    {
        return seller;
    }

    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }

    public ShippingPrice getShippingPrice()
    {
        return shippingPrice;
    }

    public void setShippingPrice(ShippingPrice shippingPrice)
    {
        this.shippingPrice = shippingPrice;
    }

    public Release getRelease()
    {
        return release;
    }

    public void setRelease(Release release)
    {
        this.release = release;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public Boolean getAudio()
    {
        return audio;
    }

    public void setAudio(Boolean audio)
    {
        this.audio = audio;
    }

    @Override
    public String getTitle()
    {
        return release.getDescription();
    }

    @Override
    public String getSubtitle()
    {
        DateFormatter dateFormatter = new DateFormatter(new DateUtilsWrapper(), new SimpleDateFormatWrapper());
        return "Posted: " + dateFormatter.formatIsoDate(posted);
    }

    @Override
    public String getThumb()
    {
        return release.getThumbnail();
    }

    @Override
    public String getType()
    {
        return "listing";
    }
}
