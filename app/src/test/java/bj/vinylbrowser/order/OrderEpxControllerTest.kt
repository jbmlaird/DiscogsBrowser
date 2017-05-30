package bj.vinylbrowser.order

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(LOLLIPOP), manifest = Config.NONE)
class OrderEpxControllerTest {
    lateinit var epxController: OrderEpxController
    val context: Context = mock()
    val view: OrderContract.View  = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val tracker: AnalyticsTracker = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        epxController = OrderEpxController(context, view, imageViewAnimator, tracker)
        epxController.requestModelBuild()
    }

    @Test
    fun initialLoadingState_correct() {
        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 1)
    }

    @Test
    fun errorThenRetry_displaysErrorThenValidData() {
        epxController.setError(true)
        var copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 1)

        epxController.setLoadingOrder(true)
        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 1)

        epxController.setOrderDetails(OrderFactory.buildOneOrderWithItems(4))
        copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "BuyerModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "TotalModel_")
        assertEquals(copyOfModels.size, 8)
    }

    @Test
    fun fourItems_displaysFourItems() {
        epxController.setOrderDetails(OrderFactory.buildOneOrderWithItems(4))
        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "BuyerModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "OrderReleaseModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "TotalModel_")
        assertEquals(copyOfModels.size, 8)
    }
}