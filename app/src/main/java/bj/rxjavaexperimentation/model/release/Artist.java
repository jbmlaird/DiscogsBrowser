
package bj.rxjavaexperimentation.model.release;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("anv")
    @Expose
    private String anv;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("join")
    @Expose
    private String join;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("tracks")
    @Expose
    private String tracks;

    public String getAnv() {
        return anv;
    }

    public void setAnv(String anv) {
        this.anv = anv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

}
