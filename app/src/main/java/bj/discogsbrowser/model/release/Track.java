
package bj.discogsbrowser.model.release;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track
{

    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type_")
    @Expose
    private String type;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
