
package bj.discogsbrowser.model.wantlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Label {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("entity_type")
    @Expose
    private String entityType;
    @SerializedName("catno")
    @Expose
    private String catno;
    @SerializedName("resource_url")
    @Expose
    private String resourceUrl;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("entity_type_name")
    @Expose
    private String entityTypeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
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

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

}
