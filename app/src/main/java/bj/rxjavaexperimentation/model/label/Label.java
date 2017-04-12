
package bj.rxjavaexperimentation.model.label;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bj.rxjavaexperimentation.model.release.Image;

public class Label {

    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("releases_url")
    @Expose
    private String releasesUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("data_quality")
    @Expose
    private String dataQuality;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getReleasesUrl() {
        return releasesUrl;
    }

    public void setReleasesUrl(String releasesUrl) {
        this.releasesUrl = releasesUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(String dataQuality) {
        this.dataQuality = dataQuality;
    }

}
