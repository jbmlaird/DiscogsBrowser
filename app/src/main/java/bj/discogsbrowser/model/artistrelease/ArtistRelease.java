
package bj.discogsbrowser.model.artistrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bj.discogsbrowser.model.common.RecyclerViewModel;

public class ArtistRelease implements RecyclerViewModel
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("main_release")
    @Expose
    private String mainRelease;
    @SerializedName("trackinfo")
    @Expose
    private String trackinfo;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getThumb()
    {
        return thumb;
    }

    public void setThumb(String thumb)
    {
        this.thumb = thumb;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public String getSubtitle()
    {
        return artist;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMainRelease()
    {
        return mainRelease;
    }

    public void setMainRelease(String mainRelease)
    {
        this.mainRelease = mainRelease;
    }

    public String getTrackinfo()
    {
        return trackinfo;
    }

    public void setTrackinfo(String trackinfo)
    {
        this.trackinfo = trackinfo;
    }

}
