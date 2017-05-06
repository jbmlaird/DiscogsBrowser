
package bj.discogsbrowser.model.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCollectionResponse
{

    @SerializedName("instance_id")
    @Expose
    private String instanceId;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;

    public String getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

}
