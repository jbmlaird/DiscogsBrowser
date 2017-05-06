
package bj.discogsbrowser.model.master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("embed")
    @Expose
    private Boolean embed;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEmbed() {
        return embed;
    }

    public void setEmbed(Boolean embed) {
        this.embed = embed;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
