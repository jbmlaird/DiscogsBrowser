
package bj.discogsbrowser.model.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.discogsbrowser.model.common.BasicInformation;
import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.utils.ArtistsBeautifier;

public class CollectionRelease implements RecyclerViewModel
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("instance_id")
    @Expose
    private String instanceId;
    @SerializedName("folder_id")
    @Expose
    private String folderId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("basic_information")
    @Expose
    private BasicInformation basicInformation;
    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;
    private String subtitle;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getFolderId()
    {
        return folderId;
    }

    public void setFolderId(String folderId)
    {
        this.folderId = folderId;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public BasicInformation getBasicInformation()
    {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation)
    {
        this.basicInformation = basicInformation;
    }

    public List<Note> getNotes()
    {
        return notes;
    }

    public void setNotes(List<Note> notes)
    {
        this.notes = notes;
    }

    @Override
    public String getTitle()
    {
        return basicInformation.getTitle();
    }

    @Override
    public String getSubtitle()
    {
        if (subtitle == null)
        {
            ArtistsBeautifier artistsBeautifier = new ArtistsBeautifier();
            subtitle = artistsBeautifier.formatArtists(basicInformation.getArtists());
        }
        return subtitle;
    }

    @Override
    public String getThumb()
    {
        return basicInformation.getThumb();
    }

    @Override
    public String getType()
    {
        return "release";
    }
}
