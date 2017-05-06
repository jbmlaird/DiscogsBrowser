
package bj.discogsbrowser.model.release;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Community {

    @SerializedName("contributors")
    @Expose
    private List<Contributor> contributors = null;
    @SerializedName("data_quality")
    @Expose
    private String dataQuality;
    @SerializedName("have")
    @Expose
    private Integer have;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("submitter")
    @Expose
    private Submitter submitter;
    @SerializedName("want")
    @Expose
    private Integer want;

    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public String getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(String dataQuality) {
        this.dataQuality = dataQuality;
    }

    public Integer getHave() {
        return have;
    }

    public void setHave(Integer have) {
        this.have = have;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public Integer getWant() {
        return want;
    }

    public void setWant(Integer want) {
        this.want = want;
    }

}
