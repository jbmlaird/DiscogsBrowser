
package bj.discogsbrowser.model.wantlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bj.discogsbrowser.model.common.BasicInformation;

public class AddToWantlistResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("basic_information")
    @Expose
    private BasicInformation basicInformation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

}
