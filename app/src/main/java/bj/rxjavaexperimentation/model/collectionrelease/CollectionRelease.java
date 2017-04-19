
package bj.rxjavaexperimentation.model.collectionrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.rxjavaexperimentation.model.common.RecyclerViewModel;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;

public class CollectionRelease implements RecyclerViewModel
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
    private String subtitle;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId)
    {
        this.instanceId = instanceId;
    }

    public Integer getFolderId()
    {
        return folderId;
    }

    public void setFolderId(Integer folderId)
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
