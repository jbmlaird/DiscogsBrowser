package bj.vinylbrowser.order

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.TestActivity
import bj.vinylbrowser.utils.ImageViewAnimator
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

/**
 * Created by Josh Laird on 31/05/2017.
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
class OrderControllerMockPresenterTest {
    @Rule @JvmField var rule = EspressoDaggerMockRule()
    @Rule @JvmField
    var mActivityTestRule = IntentsTestRule<TestActivity>(TestActivity::class.java, false, false)
    val presenter: OrderPresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    lateinit var controller: OrderController
    lateinit var epxController: OrderEpxController
    val order = OrderFactory.buildOneOrderWithItems(2)
    val orderId = "orderId"

    @Before
    @Throws(InterruptedException::class)
    fun setUp() {
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).fetchOrderDetails(orderId)
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = OrderController(orderId)
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
            controller.controller.setOrderDetails(order)
        })
        epxController = controller.controller
    }

    @Test
    fun onLoadTwoItems_initialStateCorrect() {
        val numberFormat = NumberFormat.getCurrencyInstance()
        onView(withText(orderId)).check(matches(isDisplayed()))
        onView(withText(order.status)).check(matches(isDisplayed()))
        onView(withText(order.additionalInstructions)).check(matches(isDisplayed()))
        onView(withText(order.items[0].release.description)).check(matches(isDisplayed()))
        onView(withText(order.items[1].release.description)).check(matches(isDisplayed()))
        onView(withText(order.buyer.username)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(epxController.adapter.itemCount - 1))
        onView(withText(numberFormat.format(order.items[0].price.value))).check(matches(isDisplayed()))
        onView(withText(numberFormat.format(order.items[1].price.value))).check(matches(isDisplayed()))
        onView(withText(numberFormat.format(order.total.value))).check(matches(isDisplayed()))
    }
}