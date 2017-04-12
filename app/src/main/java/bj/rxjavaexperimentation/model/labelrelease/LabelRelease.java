
package bj.rxjavaexperimentation.model.labelrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabelRelease
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("catno")
    @Expose
    private String catno;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("id")
    @Expose
    private Integer id;

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

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCatno()
    {
        return catno;
    }

    public void setCatno(String catno)
    {
        this.catno = catno;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
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

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

}
