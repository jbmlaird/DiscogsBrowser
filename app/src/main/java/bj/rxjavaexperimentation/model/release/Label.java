
package bj.rxjavaexperimentation.model.release;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Label
{

    @SerializedName("catno")
    @Expose
    private String catno;
    @SerializedName("entity_type")
    @Expose
    private String entityType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    private String thumb;

    public String getCatno()
    {
        return catno;
    }

    public void setCatno(String catno)
    {
        this.catno = catno;
    }

    public String getEntityType()
    {
        return entityType;
    }

    public void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        this.resourceUrl = resourceUrl;
    }

    public void setThumb(String thumb)
    {
        this.thumb = thumb;
    }

    public String getThumb()
    {
        return thumb;
    }
}
