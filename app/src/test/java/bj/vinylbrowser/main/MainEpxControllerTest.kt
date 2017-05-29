package bj.vinylbrowser.main

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.listing.ListingFactory
import bj.vinylbrowser.model.search.SearchResult
import bj.vinylbrowser.order.OrderFactory
import bj.vinylbrowser.utils.DateFormatter
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(LOLLIPOP), manifest = Config.NONE)
class MainEpxControllerTest {
    lateinit var epxController: MainEpxController
    val context: Context = mock()
    val view: MainContract.View = mock()
    val sharedPrefsManager: SharedPrefsManager = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val dateFormatter: DateFormatter = mock()
    val tracker: AnalyticsTracker = mock()

    @Before
    fun setup() {
        initMocks(this)
        epxController = MainEpxController(context, view, sharedPrefsManager, imageViewAnimator, dateFormatter, tracker)
        epxController.requestModelBuild()
    }

    @Test
    fun initialLoadingState_correct() {
        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun viewedHistoryError_displaysRetryModel() {
        epxController.setViewedReleasesError(true)

        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun viewedHistoryErrorThenLoad_displaysLoad() {
        epxController.setViewedReleasesError(true)

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setLoadingViewedReleases(true)

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun viewedHistoryErrorThenReload_displaysList() {
        epxController.setViewedReleasesError(true)

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setViewedReleases(ViewedReleaseFactory.buildViewedReleases(1))
        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "CarouselModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun recommendationError_displaysRetryModel() {
        epxController.setRecommendationsError(true)

        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun recommendationErrorThenReload_displaysList() {
        epxController.setRecommendationsError(true)
        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setRecommendations(listOf(SearchResult()))
        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "CarouselModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun recommendationsErrorThenLoad_displaysLoad() {
        epxController.setRecommendationsError(true)

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setLoadingRecommendations(true)

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }


    @Test
    fun emptyLists_displaysNoOrderModels() {
        epxController.setViewedReleases(emptyList())

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setRecommendations(emptyList())

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setOrders(emptyList())

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setSelling(emptyList())

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun error_displaysRetryModels() {
        epxController.setOrdersError(true)

        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun unauthorizedError_displaysVerifyEmailModels() {
        epxController.setConfirmEmail(true)

        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun errorThenRetry_displaysCorrectly() {
        epxController.setOrdersError(true)

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setOrders(emptyList())
        epxController.setSelling(emptyList())

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }

    @Test
    fun ordersList_displaysList() {
        epxController.setOrders(OrderFactory.buildListOfOrders(2))

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "OrderModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "OrderModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 11)

        epxController.setSelling(listOf(ListingFactory.buildListing("1"), ListingFactory.buildListing("2")))

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "OrderModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "OrderModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "ListingModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "ListingModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 13)
    }

    @Test
    fun overFiveItems_Concatenates() {
        //Adding 6 orders
        epxController.setOrders(OrderFactory.buildListOfOrders(6))

        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "OrderModel_") //1
        assertEquals(copyOfModels[6].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "OrderModel_") //2
        assertEquals(copyOfModels[8].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "OrderModel_") //3
        assertEquals(copyOfModels[10].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "OrderModel_") //4
        assertEquals(copyOfModels[12].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "OrderModel_") // Only 5
        assertEquals(copyOfModels[14].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[16].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[17].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 18)

        epxController.setSelling(ListingFactory.buildNumberOfListings(6))

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "OrderModel_") //1
        assertEquals(copyOfModels[6].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "OrderModel_") //2
        assertEquals(copyOfModels[8].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "OrderModel_") //3
        assertEquals(copyOfModels[10].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "OrderModel_") //4
        assertEquals(copyOfModels[12].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "OrderModel_") // Only 5
        assertEquals(copyOfModels[14].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[16].javaClass.simpleName, "ListingModel_")
        assertEquals(copyOfModels[17].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[18].javaClass.simpleName, "ListingModel_")
        assertEquals(copyOfModels[19].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[20].javaClass.simpleName, "ListingModel_")
        assertEquals(copyOfModels[21].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[22].javaClass.simpleName, "ListingModel_")
        assertEquals(copyOfModels[23].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[24].javaClass.simpleName, "ListingModel_") //Only 5
        assertEquals(copyOfModels[25].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[26].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 27)
    }

    @Test
    fun setLoadingTrue_loadingModelsDisplay() {
        epxController.setOrders(emptyList())
        epxController.setSelling(emptyList())
        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "NoOrderModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)

        epxController.setLoadingMorePurchases(true)

        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "MainTitleModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 9)
    }
}