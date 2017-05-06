
package bj.discogsbrowser.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails
{

    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("wantlist_url")
    @Expose
    private String wantlistUrl;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("num_pending")
    @Expose
    private Integer numPending;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("num_for_sale")
    @Expose
    private Integer numForSale;
    @SerializedName("home_page")
    @Expose
    private String homePage;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("collection_folders_url")
    @Expose
    private String collectionFoldersUrl;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("collection_fields_url")
    @Expose
    private String collectionFieldsUrl;
    @SerializedName("releases_contributed")
    @Expose
    private Integer releasesContributed;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("rating_avg")
    @Expose
    private Double ratingAvg;
    @SerializedName("num_collection")
    @Expose
    private Integer numCollection;
    @SerializedName("releases_rated")
    @Expose
    private Integer releasesRated;
    @SerializedName("num_lists")
    @Expose
    private Integer numLists;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("num_wantlist")
    @Expose
    private Integer numWantlist;
    @SerializedName("inventory_url")
    @Expose
    private String inventoryUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("buyer_rating")
    @Expose
    private Double buyerRating;
    @SerializedName("buyer_rating_stars")
    @Expose
    private Double buyerRatingStars;
    @SerializedName("buyer_num_ratings")
    @Expose
    private Integer buyerNumRatings;
    @SerializedName("seller_rating")
    @Expose
    private Double sellerRating;
    @SerializedName("seller_rating_stars")
    @Expose
    private Double sellerRatingStars;
    @SerializedName("seller_num_ratings")
    @Expose
    private Integer sellerNumRatings;
    @SerializedName("curr_abbr")
    @Expose
    private String currAbbr;

    public String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getWantlistUrl()
    {
        return wantlistUrl;
    }

    public void setWantlistUrl(String wantlistUrl)
    {
        this.wantlistUrl = wantlistUrl;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    public Integer getNumPending()
    {
        return numPending;
    }

    public void setNumPending(Integer numPending)
    {
        this.numPending = numPending;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getNumForSale()
    {
        return numForSale;
    }

    public void setNumForSale(Integer numForSale)
    {
        this.numForSale = numForSale;
    }

    public String getHomePage()
    {
        return homePage;
    }

    public void setHomePage(String homePage)
    {
        this.homePage = homePage;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getCollectionFoldersUrl()
    {
        return collectionFoldersUrl;
    }

    public void setCollectionFoldersUrl(String collectionFoldersUrl)
    {
        this.collectionFoldersUrl = collectionFoldersUrl;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getCollectionFieldsUrl()
    {
        return collectionFieldsUrl;
    }

    public void setCollectionFieldsUrl(String collectionFieldsUrl)
    {
        this.collectionFieldsUrl = collectionFieldsUrl;
    }

    public Integer getReleasesContributed()
    {
        return releasesContributed;
    }

    public void setReleasesContributed(Integer releasesContributed)
    {
        this.releasesContributed = releasesContributed;
    }

    public String getRegistered()
    {
        return registered;
    }

    public void setRegistered(String registered)
    {
        this.registered = registered;
    }

    public Double getRatingAvg()
    {
        return ratingAvg;
    }

    public void setRatingAvg(Double ratingAvg)
    {
        this.ratingAvg = ratingAvg;
    }

    public Integer getNumCollection()
    {
        return numCollection;
    }

    public void setNumCollection(Integer numCollection)
    {
        this.numCollection = numCollection;
    }

    public Integer getReleasesRated()
    {
        return releasesRated;
    }

    public void setReleasesRated(Integer releasesRated)
    {
        this.releasesRated = releasesRated;
    }

    public Integer getNumLists()
    {
        return numLists;
    }

    public void setNumLists(Integer numLists)
    {
        this.numLists = numLists;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getNumWantlist()
    {
        return numWantlist;
    }

    public void setNumWantlist(Integer numWantlist)
    {
        this.numWantlist = numWantlist;
    }

    public String getInventoryUrl()
    {
        return inventoryUrl;
    }

    public void setInventoryUrl(String inventoryUrl)
    {
        this.inventoryUrl = inventoryUrl;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public Double getBuyerRating()
    {
        return buyerRating;
    }

    public void setBuyerRating(Double buyerRating)
    {
        this.buyerRating = buyerRating;
    }

    public Double getBuyerRatingStars()
    {
        return buyerRatingStars;
    }

    public void setBuyerRatingStars(Double buyerRatingStars)
    {
        this.buyerRatingStars = buyerRatingStars;
    }

    public Integer getBuyerNumRatings()
    {
        return buyerNumRatings;
    }

    public void setBuyerNumRatings(Integer buyerNumRatings)
    {
        this.buyerNumRatings = buyerNumRatings;
    }

    public Double getSellerRating()
    {
        return sellerRating;
    }

    public void setSellerRating(Double sellerRating)
    {
        this.sellerRating = sellerRating;
    }

    public Double getSellerRatingStars()
    {
        return sellerRatingStars;
    }

    public void setSellerRatingStars(Double sellerRatingStars)
    {
        this.sellerRatingStars = sellerRatingStars;
    }

    public Integer getSellerNumRatings()
    {
        return sellerNumRatings;
    }

    public void setSellerNumRatings(Integer sellerNumRatings)
    {
        this.sellerNumRatings = sellerNumRatings;
    }

    public String getCurrAbbr()
    {
        return currAbbr;
    }

    public void setCurrAbbr(String currAbbr)
    {
        this.currAbbr = currAbbr;
    }

}
