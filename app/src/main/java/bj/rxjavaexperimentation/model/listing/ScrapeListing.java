package bj.rxjavaexperimentation.model.listing;

/**
 * Created by Josh Laird on 12/04/2017.
 * <p>
 * Model containing the scraped Listing information to display on a release page.
 */
public class ScrapeListing
{
    private final String price;
    private final String convertedPrice;
    private final String mediaCondition;
    private final String sleeveCondition;
    private final String sellerUrl;
    private final String sellerName;
    private final String sellerRating;
    private final String shipsFrom;
    private final String marketPlaceId;

    public ScrapeListing(String price, String convertedPrice, String mediaCondition, String sleeveCondition, String sellerUrl, String sellerName, String sellerRating, String shipsFrom, String marketPlaceId)
    {
        this.price = price;
        this.convertedPrice = convertedPrice;
        this.mediaCondition = mediaCondition;
        this.sleeveCondition = sleeveCondition;
        this.sellerUrl = sellerUrl;
        this.sellerName = sellerName;
        this.sellerRating = sellerRating;
        this.shipsFrom = shipsFrom;
        this.marketPlaceId = marketPlaceId;
    }

    public String getPrice()
    {
        return price;
    }

    public String getConvertedPrice()
    {
        return convertedPrice;
    }

    public String getMediaCondition()
    {
        return mediaCondition;
    }

    public String getSleeveCondition()
    {
        return sleeveCondition;
    }

    public String getSellerUrl()
    {
        return sellerUrl;
    }

    public String getSellerName()
    {
        return sellerName;
    }

    public String getShipsFrom()
    {
        return shipsFrom;
    }

    public String getMarketPlaceId()
    {
        return marketPlaceId;
    }

    public String getSellerRating()
    {
        return sellerRating;
    }
}
