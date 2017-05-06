
package bj.discogsbrowser.model.wantlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.discogsbrowser.model.release.Artist;

public class BasicInformation
{

    @SerializedName("formats")
    @Expose
    private List<Format> formats = null;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("labels")
    @Expose
    private List<Label> labels = null;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<Format> getFormats()
    {
        return formats;
    }

    public void setFormats(List<Format> formats)
    {
        this.formats = formats;
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

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Label> getLabels()
    {
        return labels;
    }

    public void setLabels(List<Label> labels)
    {
        this.labels = labels;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public List<Artist> getArtists()
    {
        return artists;
    }

    public void setArtists(List<Artist> artists)
    {
        this.artists = artists;
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

}
