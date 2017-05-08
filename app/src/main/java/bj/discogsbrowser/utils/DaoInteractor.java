package bj.discogsbrowser.utils;

import java.util.List;

import bj.discogsbrowser.greendao.DaoSession;
import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.greendao.SearchTermDao;
import bj.discogsbrowser.greendao.ViewedRelease;
import bj.discogsbrowser.greendao.ViewedReleaseDao;

/**
 * Created by Josh Laird on 07/05/2017.
 */
public class DaoInteractor
{
    private ViewedReleaseDao viewedReleaseDao;
    private SearchTermDao searchTermDao;

    public DaoInteractor(DaoSession daoSession)
    {
        viewedReleaseDao = daoSession.getViewedReleaseDao();
        searchTermDao = daoSession.getSearchTermDao();
    }

    public List<ViewedRelease> getViewedReleases()
    {
        return viewedReleaseDao.queryBuilder().orderDesc(ViewedReleaseDao.Properties.Date).limit(6).build().list();
    }

    public void storeViewedRelease(ViewedRelease viewedRelease)
    {
        viewedReleaseDao.insertOrReplace(viewedRelease);
    }

    public List<SearchTerm> getRecentSearchTerms()
    {
        return searchTermDao.queryBuilder().orderDesc(SearchTermDao.Properties.Date).build().list();
    }

    public void storeSearchTerm(SearchTerm searchTerm)
    {
        searchTermDao.insertOrReplace(searchTerm);
    }
}
