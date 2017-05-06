
package bj.discogsbrowser.model.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Version
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
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("major_formats")
    @Expose
    private List<String> majorFormats = null;
    @SerializedName("catno")
    @Expose
    private String catno;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("id")
    @Expose
    private String id;

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

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getReleased()
    {
        return released;
    }

    public void setReleased(String released)
    {
        this.released = released;
    }

    public List<String> getMajorFormats()
    {
        return majorFormats;
    }

    public void setMajorFormats(List<String> majorFormats)
    {
        this.majorFormats = majorFormats;
    }

    public String getCatno()
    {
        return catno;
    }

    public void setCatno(String catno)
    {
        this.catno = catno;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

}
