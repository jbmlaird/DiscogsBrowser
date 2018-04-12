package bj.vinylbrowser.singlelist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.listing.ListingFactory
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.RouterAttacher
import bj.vinylbrowser.marketplace.MarketplaceController
import bj.vinylbrowser.order.OrderController
import bj.vinylbrowser.order.OrderFactory
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class SingleListControllerMockPresenterTest {
    @Rule
    @JvmField
    var rule = EspressoDaggerMockRule()
    @Rule
    @JvmField
    var mActivityTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java, false, false)
    val presenter: SingleListPresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val tracker: AnalyticsTracker = mock()
    val routerAttacher: RouterAttacher = mock()
    val sharedPrefsManager: SharedPrefsManager = mock()
    val type = R.string.orders
    val username = "dasmebro"
    lateinit var controller: SingleListController
    lateinit var epxController: SingleListEpxController

    @Before
    fun setUp() {
        doAnswer { invocationOnMock -> invocationOnMock }.whenever(routerAttacher).attachRoot(any())
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).getData(any(), any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(tracker).send(any(), any(), any(), any(), any())
        whenever(sharedPrefsManager.isUserLoggedIn).thenReturn(true)
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = SingleListController(type, username)
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
            Thread.sleep(500)
        })
        epxController = controller.epxController
    }

    @Test
    fun sixOrders_displaysAndIntends() {
        val listOfSix = OrderFactory.buildListOfOrders(6)
        epxController.setItems(listOfSix) // Use this method to call requestModelBuild()
        onView(withText(listOfSix[0].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, listOfSix[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withText(listOfSix[1].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, listOfSix[1].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withText(listOfSix[2].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, listOfSix[2].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(epxController.adapter.itemCount - 1))
        onView(withText(listOfSix[3].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, listOfSix[3].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(epxController.adapter.itemCount - 1))
        onView(withText(listOfSix[4].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, listOfSix[4].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(epxController.adapter.itemCount - 1))
        onView(withText(listOfSix[5].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, listOfSix[5].id)
    }

    @Test
    fun loadThreeWants_displaysAndIntends() {
        val threeWants = WantFactory.getThreeWants()
        epxController.setItems(threeWants)

        onView(withText(threeWants[0].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, threeWants[0].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, threeWants[0].getTitle())
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withText(threeWants[1].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, threeWants[1].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, threeWants[1].getTitle())
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withText(threeWants[2].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, threeWants[2].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, threeWants[2].getTitle())
    }

    @Test
    fun loadThreeSelling_displaysAndIntends() {
        val threeListings = ListingFactory.buildNumberOfListings(3)
        epxController.setItems(threeListings)
        Thread.sleep(1000)

        onView(withText(threeListings[0].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(1500)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, threeListings[0].id)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, threeListings[0].getTitle())
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(1000)

        onView(withText(threeListings[1].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(1000)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, threeListings[1].id)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, threeListings[1].getTitle())
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(1000)

        onView(withText(threeListings[2].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(1000)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, threeListings[2].id)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, threeListings[2].getTitle())
    }

    @Test
    fun loadThreeCollection_displaysAndIntends() {
        val threeCollectionReleases = CollectionFactory.getThreeCollectionReleases()
        epxController.items = threeCollectionReleases

        onView(withText(threeCollectionReleases[0].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, threeCollectionReleases[0].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, threeCollectionReleases[0].getTitle())
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withText(threeCollectionReleases[1].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, threeCollectionReleases[1].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, threeCollectionReleases[1].getTitle())
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withText(threeCollectionReleases[2].getTitle())).check(matches(isDisplayed())).perform(click())
        Thread.sleep(500)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, threeCollectionReleases[2].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, threeCollectionReleases[2].getTitle())
    }
}