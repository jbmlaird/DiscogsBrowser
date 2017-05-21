package bj.discogsbrowser.utils;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.greenrobot.greendao.database.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.greendao.DaoMaster;
import bj.discogsbrowser.greendao.DaoSession;
import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.greendao.ViewedRelease;
import bj.discogsbrowser.release.ReleaseFactory;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 17/05/2017.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class GreendaoTest
{
    private DaoManager daoManager;
    private ArtistsBeautifier artistsBeautifier;
    private String id = "123";

    @Before
    public void setUp()
    {
        artistsBeautifier = new ArtistsBeautifier();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(InstrumentationRegistry.getTargetContext(), "search-db");
        // Use when changing schema
        // helper.onUpgrade(helper.getWritableDatabase(), 7, 8);
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        daoManager = new DaoManager(daoSession);
        daoManager.clearRecentSearchTerms();
        daoManager.clearViewedReleases();
    }

    @After
    public void tearDown()
    {
        daoManager.clearRecentSearchTerms();
        daoManager.clearViewedReleases();
    }

    @Test
    public void addAndClear_zeroSizeList() throws InterruptedException
    {
        assertEquals(daoManager.getRecentSearchTerms().size(), 0);
        assertEquals(daoManager.getViewedReleases().size(), 0);

        daoManager.storeSearchTerm("ye");
        daoManager.storeViewedRelease(ReleaseFactory.buildReleaseWithLabelNoneForSale(id), artistsBeautifier);

        assertEquals(daoManager.getRecentSearchTerms().size(), 1);
        assertEquals(daoManager.getViewedReleases().size(), 1);

        daoManager.clearRecentSearchTerms();
        assertEquals(daoManager.getRecentSearchTerms().size(), 0);
        daoManager.clearViewedReleases();
        assertEquals(daoManager.getViewedReleases().size(), 0);
    }

    @Test
    public void addDuplicate_sizeStaysSame() throws InterruptedException
    {
        assertEquals(daoManager.getRecentSearchTerms().size(), 0);
        assertEquals(daoManager.getViewedReleases().size(), 0);

        daoManager.storeSearchTerm("ye");
        daoManager.storeSearchTerm("ye");
        daoManager.storeViewedRelease(ReleaseFactory.buildReleaseWithLabelNoneForSale(id), artistsBeautifier);
        daoManager.storeViewedRelease(ReleaseFactory.buildReleaseWithLabelNoneForSale(id), artistsBeautifier);

        assertEquals(daoManager.getRecentSearchTerms().size(), 1);
        assertEquals(daoManager.getViewedReleases().size(), 1);
    }

    @Test
    public void addMoreThanTwelve_deletesFirstValuesLimitTwelve() throws InterruptedException
    {
        List<SearchTerm> recentSearchTerms = daoManager.getRecentSearchTerms();
        assertEquals(recentSearchTerms.size(), 0);
        List<ViewedRelease> viewedReleases = daoManager.getViewedReleases();
        assertEquals(viewedReleases.size(), 0);

        for (int i = 0; i < 15; i++)
        {
            daoManager.storeSearchTerm(String.valueOf(i));
            daoManager.storeViewedRelease(ReleaseFactory.buildRelease(id, String.valueOf(i), false, false, false, 0, 0, 0), artistsBeautifier);
        }

        viewedReleases = daoManager.getViewedReleases();
        recentSearchTerms = daoManager.getRecentSearchTerms();
        assertEquals(recentSearchTerms.size(), 12);
        assertEquals(viewedReleases.size(), 12);
        System.out.println(recentSearchTerms.get(11).getSearchTerm());
        System.out.println(viewedReleases.get(11).getReleaseId());
//        assertTrue(recentSearchTerms.get(11).getSearchTerm().equals("3"));
//        assertTrue(viewedReleases.get(11).getReleaseId().equals("releaseId3"));
    }
}
