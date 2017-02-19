
package bj.rxjavaexperimentation.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result
{

    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("style")
    @Expose
    private List<String> style = null;
    @SerializedName("format")
    @Expose
    private List<String> format = null;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("barcode")
    @Expose
    private List<String> barcode = null;
    @SerializedName("community")
    @Expose
    private Community community;
    @SerializedName("label")
    @Expose
    private List<String> label = null;
    @SerializedName("catno")
    @Expose
    private String catno;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("genre")
    @Expose
    private List<String> genre = null;

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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<String> getStyle()
    {
        return style;
    }

    public void setStyle(List<String> style)
    {
        this.style = style;
    }

    public List<String> getFormat()
    {
        return format;
    }

    public void setFormat(List<String> format)
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

    public List<String> getBarcode()
    {
        return barcode;
    }

    public void setBarcode(List<String> barcode)
    {
        this.barcode = barcode;
    }

    public Community getCommunity()
    {
        return community;
    }

    public void setCommunity(Community community)
    {
        this.community = community;
    }

    public List<String> getLabel()
    {
        return label;
    }

    public void setLabel(List<String> label)
    {
        this.label = label;
    }

    public String getCatno()
    {
        return catno;
    }

    public void setCatno(String catno)
    {
        this.catno = catno;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public List<String> getGenre()
    {
        return genre;
    }

    public void setGenre(List<String> genre)
    {
        this.genre = genre;
    }

}
