
package bj.discogsbrowser.model.listing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.discogsbrowser.model.common.Pagination;

public class RootListingResponse
{

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("listings")
    @Expose
    private List<Listing> listings = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<Listing> getListings()
    {
        return listings;
    }

    public void setListings(List<Listing> listings)
    {
        this.listings = listings;
    }

}
