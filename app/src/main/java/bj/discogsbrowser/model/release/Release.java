
package bj.discogsbrowser.model.release;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.model.common.Artist;

public class Release
{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("data_quality")
    @Expose
    private String dataQuality;
    @SerializedName("thumb")
    @Expose
    private String thumb = "";
    @SerializedName("community")
    @Expose
    private Community community;
    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("date_changed")
    @Expose
    private String dateChanged;
    @SerializedName("estimated_weight")
    @Expose
    private Integer estimatedWeight;
    @SerializedName("extraartists")
    @Expose
    private List<Extraartist> extraartists = null;
    @SerializedName("format_quantity")
    @Expose
    private Integer formatQuantity;
    @SerializedName("formats")
    @Expose
    private List<Format> formats = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("identifiers")
    @Expose
    private List<Identifier> identifiers = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("labels")
    @Expose
    private List<Label> labels = null;
    @SerializedName("lowest_price")
    @Expose
    private Double lowestPrice;
    @SerializedName("master_id")
    @Expose
    private Integer masterId;
    @SerializedName("master_url")
    @Expose
    private String masterUrl;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("num_for_sale")
    @Expose
    private Integer numForSale;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("released_formatted")
    @Expose
    private String releasedFormatted;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("series")
    @Expose
    private List<Object> series = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("styles")
    @Expose
    private List<String> styles = null;
    @SerializedName("tracklist")
    @Expose
    private List<Track> track = new ArrayList<>();
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    @SerializedName("year")
    @Expose
    private Integer year;
    private String lowestPriceString;
    private boolean isInCollection;
    private boolean isInWantlist;
    private String instanceId;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<Artist> getArtists()
    {
        return artists;
    }

    public void setArtists(List<Artist> artists)
    {
        this.artists = artists;
    }

    public String getDataQuality()
    {
        return dataQuality;
    }

    public void setDataQuality(String dataQuality)
    {
        this.dataQuality = dataQuality;
    }

    public String getThumb()
    {
        return thumb;
    }

    public void setThumb(String thumb)
    {
        this.thumb = thumb;
    }

    public Community getCommunity()
    {
        return community;
    }

    public void setCommunity(Community community)
    {
        this.community = community;
    }

    public List<Company> getCompanies()
    {
        return companies;
    }

    public void setCompanies(List<Company> companies)
    {
        this.companies = companies;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public String getDateChanged()
    {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged)
    {
        this.dateChanged = dateChanged;
    }

    public Integer getEstimatedWeight()
    {
        return estimatedWeight;
    }

    public void setEstimatedWeight(Integer estimatedWeight)
    {
        this.estimatedWeight = estimatedWeight;
    }

    public List<Extraartist> getExtraartists()
    {
        return extraartists;
    }

    public void setExtraartists(List<Extraartist> extraartists)
    {
        this.extraartists = extraartists;
    }

    public Integer getFormatQuantity()
    {
        return formatQuantity;
    }

    public void setFormatQuantity(Integer formatQuantity)
    {
        this.formatQuantity = formatQuantity;
    }

    public List<Format> getFormats()
    {
        return formats;
    }

    public void setFormats(List<Format> formats)
    {
        this.formats = formats;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public List<Identifier> getIdentifiers()
    {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers)
    {
        this.identifiers = identifiers;
    }

    public List<Image> getImages()
    {
        return images;
    }

    public void setImages(List<Image> images)
    {
        this.images = images;
    }

    public List<Label> getLabels()
    {
        return labels;
    }

    public void setLabels(List<Label> labels)
    {
        this.labels = labels;
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

    public Integer getMasterId()
    {
        return masterId;
    }

    public void setMasterId(Integer masterId)
    {
        this.masterId = masterId;
    }

    public String getMasterUrl()
    {
        return masterUrl;
    }

    public void setMasterUrl(String masterUrl)
    {
        this.masterUrl = masterUrl;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public Integer getNumForSale()
    {
        return numForSale;
    }

    public void setNumForSale(Integer numForSale)
    {
        this.numForSale = numForSale;
    }

    public String getReleased()
    {
        return released;
    }

    public void setReleased(String released)
    {
        this.released = released;
    }

    public String getReleasedFormatted()
    {
        return releasedFormatted;
    }

    public void setReleasedFormatted(String releasedFormatted)
    {
        this.releasedFormatted = releasedFormatted;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public List<Object> getSeries()
    {
        return series;
    }

    public void setSeries(List<Object> series)
    {
        this.series = series;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<String> getStyles()
    {
        return styles;
    }

    public void setStyles(List<String> styles)
    {
        this.styles = styles;
    }

    public List<Track> getTracklist()
    {
        return track;
    }

    public void setTracklist(List<Track> track)
    {
        this.track = track;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public List<Video> getVideos()
    {
        return videos;
    }

    public void setVideos(List<Video> videos)
    {
        this.videos = videos;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public void setIsInCollection(boolean b)
    {
        isInCollection = b;
    }

    public void setIsInWantlist(boolean b)
    {
        isInWantlist = b;
    }

    public boolean isInCollection()
    {
        return isInCollection;
    }

    public boolean isInWantlist()
    {
        return isInWantlist;
    }

    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getInstanceId()
    {
        return instanceId;
    }
}
