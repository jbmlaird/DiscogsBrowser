package bj.rxjavaexperimentation.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@Entity
public class SearchTerm
{
    @Id private Long id;
    @Unique
    private String searchTerm;
    private Date date;

    @Generated(hash = 1581228086)
    public SearchTerm(Long id, String searchTerm, Date date)
    {
        this.id = id;
        this.searchTerm = searchTerm;
        this.date = date;
    }

    @Generated(hash = 798575956)
    public SearchTerm()
    {
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSearchTerm()
    {
        return this.searchTerm;
    }

    public void setSearchTerm(String searchTerm)
    {
        this.searchTerm = searchTerm;
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
