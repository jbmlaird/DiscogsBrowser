package bj.discogsbrowser.utils;

import android.text.TextUtils;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.Date;
import java.util.List;

import bj.discogsbrowser.greendao.DaoSession;
import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.greendao.SearchTermDao;
import bj.discogsbrowser.greendao.ViewedRelease;
import bj.discogsbrowser.greendao.ViewedReleaseDao;
import bj.discogsbrowser.model.release.Release;

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
        return viewedReleaseDao.queryBuilder().orderDesc(ViewedReleaseDao.Properties.Date).build().list();
    }

    public void storeViewedRelease(Release release, ArtistsBeautifier artistsBeautifier)
    {
        ViewedRelease viewedRelease = new ViewedRelease();
        if (release.getStyles() != null && release.getGenres().size() > 0)
            viewedRelease.setStyle(TextUtils.join(",", release.getStyles()));
        viewedRelease.setReleaseId(release.getId());
        if (release.getImages() != null && release.getImages().size() > 0)
            viewedRelease.setThumbUrl(release.getImages().get(0).getResourceUrl());
        else
            viewedRelease.setThumbUrl(release.getThumb());
        viewedRelease.setDate(new Date());
        viewedRelease.setReleaseName(release.getTitle());
        if (release.getLabels() != null && release.getLabels().size() > 0)
            viewedRelease.setLabelName(release.getLabels().get(0).getName());
        if (release.getArtists() != null && release.getArtists().size() > 0)
            viewedRelease.setArtists(artistsBeautifier.formatArtists(release.getArtists()));

        // Delete the last entry (oldest) to preserve 12 items in recently viewed
        List<ViewedRelease> viewedReleases = getViewedReleases();
        if (viewedReleases.size() == 12)
            viewedReleaseDao.delete(viewedReleases.get(11));
        viewedReleaseDao.insertOrReplace(viewedRelease);
    }

    public List<SearchTerm> getRecentSearchTerms()
    {
        return searchTermDao.queryBuilder().orderDesc(SearchTermDao.Properties.Date).build().list();
    }

    public void storeSearchTerm(SearchViewQueryTextEvent queryTextEvent)
    {
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setSearchTerm(queryTextEvent.queryText().toString());
        searchTerm.setDate(new Date());
        // Delete the last entry (oldest) to preserve 12 items in recent searches
        List<SearchTerm> recentSearchTerms = getRecentSearchTerms();
        if (recentSearchTerms.size() == 12)
            searchTermDao.delete(recentSearchTerms.get(11));
        searchTermDao.insertOrReplace(searchTerm);
    }
}
