
package bj.rxjavaexperimentation.model.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.rxjavaexperimentation.model.release.Artist;
import bj.rxjavaexperimentation.model.release.Image;

public class Master
{

    @SerializedName("styles")
    @Expose
    private List<String> styles = null;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("versions_url")
    @Expose
    private String versionsUrl;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tracklist")
    @Expose
    private List<Tracklist> tracklist = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("num_for_sale")
    @Expose
    private Integer numForSale;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("main_release")
    @Expose
    private Integer mainRelease;
    @SerializedName("main_release_url")
    @Expose
    private String mainReleaseUrl;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("lowest_price")
    @Expose
    private Double lowestPrice;
    @SerializedName("data_quality")
    @Expose
    private String dataQuality;
    private String lowestPriceString;

    public List<String> getStyles()
    {
        return styles;
    }

    public void setStyles(List<String> styles)
    {
        this.styles = styles;
    }

    public List<Video> getVideos()
    {
        return videos;
    }

    public void setVideos(List<Video> videos)
    {
        this.videos = videos;
    }

    public List<Artist> getArtists()
    {
        return artists;
    }

    public void setArtists(List<Artist> artists)
    {
        this.artists = artists;
    }

    public String getVersionsUrl()
    {
        return versionsUrl;
    }

    public void setVersionsUrl(String versionsUrl)
    {
        this.versionsUrl = versionsUrl;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public List<Image> getImages()
    {
        return images;
    }

    public void setImages(List<Image> images)
    {
        this.images = images;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<Tracklist> getTracklist()
    {
        return tracklist;
    }

    public void setTracklist(List<Tracklist> tracklist)
    {
        this.tracklist = tracklist;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public Integer getNumForSale()
    {
        return numForSale;
    }

    public void setNumForSale(Integer numForSale)
    {
        this.numForSale = numForSale;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getMainRelease()
    {
        return mainRelease;
    }

    public void setMainRelease(Integer mainRelease)
    {
        this.mainRelease = mainRelease;
    }

    public String getMainReleaseUrl()
    {
        return mainReleaseUrl;
    }

    public void setMainReleaseUrl(String mainReleaseUrl)
    {
        this.mainReleaseUrl = mainReleaseUrl;
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

    public Double getLowestPrice()
    {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice)
    {
        this.lowestPrice = lowestPrice;
    }

    public String getLowestPriceString()
    {
        return lowestPriceString;
    }

    public void setLowestPriceString(String lowestPriceString)
    {
        this.lowestPriceString = lowestPriceString;
    }

    public String getDataQuality()
    {
        return dataQuality;
    }

    public void setDataQuality(String dataQuality)
    {
        this.dataQuality = dataQuality;
    }

}
