package bj.rxjavaexperimentation.model.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.model.release.Image;

public class ArtistResult
{
    @SerializedName("namevariations")
    @Expose
    private List<String> namevariations = new ArrayList<>();
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("releases_url")
    @Expose
    private String releasesUrl;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("urls")
    @Expose
    private List<String> urls = null;
    @SerializedName("data_quality")
    @Expose
    private String dataQuality;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;

    public List<String> getNamevariations()
    {
        return namevariations;
    }

    public void setNamevariations(List<String> namevariations)
    {
        this.namevariations = namevariations;
    }

    public String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getReleasesUrl()
    {
        return releasesUrl;
    }

    public void setReleasesUrl(String releasesUrl)
    {
        this.releasesUrl = releasesUrl;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public List<String> getUrls()
    {
        return urls;
    }

    public void setUrls(List<String> urls)
    {
        this.urls = urls;
    }

    public String getDataQuality()
    {
        return dataQuality;
    }

    public void setDataQuality(String dataQuality)
    {
        this.dataQuality = dataQuality;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<Image> getImages()
    {
        return images;
    }

    public void setImages(List<Image> images)
    {
        this.images = images;
    }

    public List<Member> getMembers()
    {
        return members;
    }

    public void setMembers(List<Member> members)
    {
        this.members = members;
    }

}
