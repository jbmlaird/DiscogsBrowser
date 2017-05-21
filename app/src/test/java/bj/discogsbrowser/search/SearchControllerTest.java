package bj.discogsbrowser.search;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.greendao.SearchTerm;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 02/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class SearchControllerTest
{
    private SearchController controller;
    @Mock Context context;
    @Mock SearchContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock AnalyticsTracker tracker;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new SearchController(context, view, imageViewAnimator, tracker);
        controller.requestModelBuild();
    }

    @Test
    public void initialStateNoSearchTerms_displaysNothing()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.size(), 0);
    }

    @Test
    public void initialStateTwoSearchTerms_displaysTwoSearchTerms()
    {
        controller.setPastSearches(Arrays.asList(SearchFactory.buildPastSearch(), SearchFactory.buildPastSearch()));
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("PastSearchModel_"));
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), ("PastSearchModel_"));
        assertEquals(copyOfModels.size(), 2);
    }

    @Test
    public void searchThreeResults_showsResults()
    {
        controller.setSearching(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("LoadingModel_"));
        assertEquals(copyOfModels.size(), 1);

        controller.setSearchResults(SearchFactory.buildThreeSearchResults());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void searchNoResults_showNoResults()
    {
        controller.setSearching(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("LoadingModel_"));
        assertEquals(copyOfModels.size(), 1);

        controller.setSearchResults(Collections.emptyList());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("CenterTextModel_"));
        assertEquals(copyOfModels.size(), 1);
    }

    @Test
    public void errorThenSearch_showResults()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("RetryModel_"));
        assertEquals(copyOfModels.size(), 1);

        controller.setSearching(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("LoadingModel_"));
        assertEquals(copyOfModels.size(), 1);

        controller.setSearchResults(SearchFactory.buildThreeSearchResults());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void resultsThenClear_showsPastSearches()
    {
        controller.setSearchResults(SearchFactory.buildThreeSearchResults());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), ("SearchResultModel_"));
        assertEquals(copyOfModels.size(), 3);

        controller.setShowPastSearches(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.size(), 0);

        controller.setPastSearches(Arrays.asList(new SearchTerm()));
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), ("PastSearchModel_"));
        assertEquals(copyOfModels.size(), 1);
    }
}
