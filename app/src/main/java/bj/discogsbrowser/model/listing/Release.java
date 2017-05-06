
package bj.discogsbrowser.model.listing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Release {

    @SerializedName("catalog_number")
    @Expose
    private String catalogNumber;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
