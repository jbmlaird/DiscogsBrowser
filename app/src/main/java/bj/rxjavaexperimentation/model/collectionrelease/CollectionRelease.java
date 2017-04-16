
package bj.rxjavaexperimentation.model.collectionrelease;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionRelease
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instance_id")
    @Expose
    private Integer instanceId;
    @SerializedName("folder_id")
    @Expose
    private Integer folderId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("basic_information")
    @Expose
    private BasicInformation basicInformation;
    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
