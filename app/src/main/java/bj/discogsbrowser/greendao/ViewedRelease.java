package bj.discogsbrowser.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Josh Laird on 05/05/2017.
 */
@Entity
public class ViewedRelease
{
    @Id private Long id;
    @Unique
    private String releaseId;
    private String style;
    private Date date;
    private String thumbUrl;
    private String releaseName;
    private String labelName;
    private String artists;
    @Generated(hash = 1880159999)
    public ViewedRelease(Long id, String releaseId, String style, Date date,
            String thumbUrl, String releaseName, String labelName, String artists) {
        this.id = id;
        this.releaseId = releaseId;
        this.style = style;
        this.date = date;
        this.thumbUrl = thumbUrl;
        this.releaseName = releaseName;
        this.labelName = labelName;
        this.artists = artists;
    }
    @Generated(hash = 2066259399)
    public ViewedRelease() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReleaseId() {
        return this.releaseId;
    }
    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }
    public String getStyle() {
        return this.style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getThumbUrl() {
        return this.thumbUrl;
    }
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
    public String getReleaseName() {
        return this.releaseName;
    }
    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }
    public String getLabelName() {
        return this.labelName;
    }
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
    public String getArtists() {
        return this.artists;
    }
    public void setArtists(String artists) {
        this.artists = artists;
    }
}
