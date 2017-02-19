
package bj.rxjavaexperimentation.discogs.gson.release;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Submitter {

    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("username")
    @Expose
    private String username;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
