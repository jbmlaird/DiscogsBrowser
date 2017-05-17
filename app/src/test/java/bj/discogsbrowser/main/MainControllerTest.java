package bj.discogsbrowser.main;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.After;
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

import bj.discogsbrowser.greendao.DaoSession;
import bj.discogsbrowser.marketplace.TestListing;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.order.OrderFactory;
import bj.discogsbrowser.utils.DateFormatter;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 01/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class MainControllerTest
{
    private MainController controller;
    @Mock Context context;
    @Mock MainContract.View view;
    @Mock SharedPrefsManager sharedPrefsManager;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock DateFormatter dateFormatter;
    @Mock AnalyticsTracker tracker;
    @Mock DaoSession daoSession;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new MainController(context, view, sharedPrefsManager, imageViewAnimator, dateFormatter, tracker);
        controller.requestModelBuild();
    }

    @After
    public void tearDown()
    {
        // Not too fussed about the interactions, just want to make sure that the correct models build
//        verifyNoMoreInteractions(context, view, sharedPrefsManager, imageViewAnimator, dateFormatter);
    }

    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void viewedHistoryError_displaysRetryModel()
    {
        controller.setViewedReleasesError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void viewedHistoryErrorThenLoad_displaysLoad()
    {
        controller.setViewedReleasesError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setLoadingViewedReleases(true);

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void viewedHistoryErrorThenReload_displaysList()
    {
        controller.setViewedReleasesError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setViewedReleases(Arrays.asList(MainFactory.buildViewedRelease("123")));
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "CarouselModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void recommendationError_displaysRetryModel()
    {
        controller.setRecommendationsError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void recommendationErrorThenReload_displaysList()
    {
        controller.setRecommendationsError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setRecommendations(Arrays.asList(new SearchResult()));
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "CarouselModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void recommendationsErrorThenLoad_displaysLoad()
    {
        controller.setRecommendationsError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setLoadingRecommendations(true);

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }


    @Test
    public void emptyLists_displaysNoOrderModels()
    {
        controller.setViewedReleases(Collections.emptyList());

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setRecommendations(Collections.emptyList());

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setOrders(Collections.emptyList());

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setSelling(Collections.emptyList());

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void error_displaysRetryModels()
    {
        controller.setOrdersError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void unauthorizedError_displaysVerifyEmailModels()
    {
        controller.setConfirmEmail(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void errorThenRetry_displaysCorrectly()
    {
        controller.setOrdersError(true);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setOrders(Collections.emptyList());
        controller.setSelling(Collections.emptyList());

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }

    @Test
    public void ordersList_displaysList()
    {
        controller.setOrders(OrderFactory.getListOfTwo());

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "OrderModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "OrderModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 11);

        controller.setSelling(Arrays.asList(new TestListing(), new TestListing()));

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "OrderModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "OrderModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "ListingModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "ListingModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 13);
    }

    @Test
    public void overFiveItems_Concatenates()
    {
        //Adding 6 orders
        controller.setOrders(OrderFactory.getListOfSix());

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "OrderModel_"); //1
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "OrderModel_"); //2
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "OrderModel_"); //3
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "OrderModel_"); //4
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "OrderModel_"); // Only 5
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(17).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 18);

        controller.setSelling(Arrays.asList(new TestListing(), new TestListing(),
                new TestListing(), new TestListing(), new TestListing(), new TestListing()));

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "OrderModel_"); //1
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "OrderModel_"); //2
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "OrderModel_"); //3
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "OrderModel_"); //4
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "OrderModel_"); // Only 5
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "ListingModel_");
        assertEquals(copyOfModels.get(17).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(18).getClass().getSimpleName(), "ListingModel_");
        assertEquals(copyOfModels.get(19).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(20).getClass().getSimpleName(), "ListingModel_");
        assertEquals(copyOfModels.get(21).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(22).getClass().getSimpleName(), "ListingModel_");
        assertEquals(copyOfModels.get(23).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(24).getClass().getSimpleName(), "ListingModel_"); //Only 5
        assertEquals(copyOfModels.get(25).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(26).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 27);
    }

    @Test
    public void setLoadingTrue_loadingModelsDisplay()
    {
        controller.setOrders(Collections.emptyList());
        controller.setSelling(Collections.emptyList());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "NoOrderModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);

        controller.setLoadingMorePurchases(true);

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "MainTitleModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 9);
    }
}