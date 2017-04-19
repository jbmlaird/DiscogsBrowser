
package bj.rxjavaexperimentation.model.wantlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bj.rxjavaexperimentation.model.common.RecyclerViewModel;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;

public class Want implements RecyclerViewModel
{
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("basic_information")
    @Expose
    private BasicInformation basicInformation;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("notes")
    @Expose
    private String notes;
    private String subtitle;

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public BasicInformation getBasicInformation()
    {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation)
    {
        this.basicInformation = basicInformation;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    @Override
    public String getTitle()
    {
        return basicInformation.getTitle();
    }

    @Override
    public String getSubtitle()
    {
        if (subtitle == null)
        {
            ArtistsBeautifier artistsBeautifier = new ArtistsBeautifier();
            subtitle = artistsBeautifier.formatArtists(basicInformation.getArtists());
        }
        return subtitle;
    }

    @Override
    public String getThumb()
    {
        return basicInformation.getThumb();
    }

    @Override
    public String getType()
    {
        return "release";
    }
}
