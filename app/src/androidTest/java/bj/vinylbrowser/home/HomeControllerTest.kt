package bj.vinylbrowser.home

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.testutils.TestActivity
import bj.vinylbrowser.customviews.Carousel
import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.listing.ListingFactory
import bj.vinylbrowser.marketplace.MarketplaceController
import bj.vinylbrowser.order.OrderController
import bj.vinylbrowser.order.OrderFactory
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.search.SearchResultFactory
import bj.vinylbrowser.singlelist.SingleListController
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.EspressoDaggerMockRule.getApp
import bj.vinylbrowser.testutils.TestUtils
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.NavigationDrawerBuilder
import bj.vinylbrowser.utils.SharedPrefsManager
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.nhaarman.mockito_kotlin.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 * Due to the Roboletric tests which test the models have been built, these Espresso tests only test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class HomeControllerTest {
    @Rule @JvmField val rule = EspressoDaggerMockRule()
    @Rule @JvmField
    val mActivityTestRule: IntentsTestRule<TestActivity> = IntentsTestRule(TestActivity::class.java, false, false)
    val imageViewAnimator: ImageViewAnimator = mock()
    val presenter: HomePresenter = mock()
    val sharedPrefsManager: SharedPrefsManager = mock()
    lateinit var navigationDrawerBuilder: NavigationDrawerBuilder
    val numCollection = "50"
    val numWantlist = "90"
    lateinit var controller: HomeController
    lateinit var epxController: HomeEpxController
    val fourViewedReleases = ViewedReleaseFactory.buildViewedReleases(4)
    val recommendations = SearchResultFactory.getThreeReleases()
    val orders = OrderFactory.buildListOfOrders(2)
    val listings = ListingFactory.buildNumberOfListings(3)
    val daoManager: DaoManager = mock()

    @Before
    fun setUp() {
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).connectAndBuildNavigationDrawer(any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).buildRecommendations()
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).buildViewedReleases()
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = HomeController()
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
        })
        epxController = controller.controller
        navigationDrawerBuilder = NavigationDrawerBuilder(getApp(), sharedPrefsManager, daoManager)
        initialiseUi()
    }

    @Test
    @Throws(InterruptedException::class)
    fun navDrawerPressed_intendsCorrectly() {
        onView(withText(numCollection)).check(matches(isDisplayed()))
        onView(withText(numWantlist)).check(matches(isDisplayed()))

        onView(allOf(withText("Collection"), withResourceName("material_drawer_name"))).perform(click())
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).username, "BjLairy")
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).type, R.string.drawer_item_collection)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(TestUtils.getHamburgerButton()).perform(click())
        onView(allOf(withText("Wantlist"), withResourceName("material_drawer_name"))).perform(click())
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).username, "BjLairy")
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).type, R.string.drawer_item_wantlist)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(TestUtils.getHamburgerButton()).perform(click())
        onView(allOf(withText("Marketplace"), withResourceName("material_drawer_name"))).perform(click())
        onView(allOf(withText("Selling"), withResourceName("material_drawer_name"))).perform(click())
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).username, "BjLairy")
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).type, R.string.selling)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(TestUtils.getHamburgerButton()).perform(click())
        onView(allOf(withText("Orders"), withResourceName("material_drawer_name"))).perform(click())
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).username, "BjLairy")
        assertEquals((controller.router.getControllerWithTag("SingleListController") as SingleListController).type, R.string.orders)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(TestUtils.getHamburgerButton()).perform(click())
        onView(allOf(withText("Search"), withResourceName("material_drawer_name"))).perform(click())
        assertNotNull(controller.router.getControllerWithTag("SearchController"))
        closeSoftKeyboard()
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(TestUtils.getHamburgerButton()).perform(click())
        onView(allOf(withText("About"), withResourceName("material_drawer_name"))).perform(click())
        onView(TestUtils.getHamburgerButton()).perform(click()) // As this is an Activity (not a Controller) use this to go back

        onView(TestUtils.getHamburgerButton()).perform(click())
        onView(allOf(withText("Logout"), withResourceName("material_drawer_name"))).perform(click())

        verify(daoManager).clearRecentSearchTerms()
        verify(daoManager).clearViewedReleases()
    }

    @Test
    @Throws(InterruptedException::class)
    fun viewedReleases_intendCorrectly() {
        onView(withId(R.id.lytMainContent)).perform(swipeLeft())

        controller.controller.setViewedReleases(fourViewedReleases)
        Thread.sleep(500)
        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[0].artists + " - " + fourViewedReleases[0].releaseName))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, fourViewedReleases[0].releaseName)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, fourViewedReleases[0].releaseId)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        // Have to scroll to items before trying to click them otherwise Espresso won't be able to find views off screen
        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[1].artists + " - " + fourViewedReleases[1].releaseName))))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[1].artists + " - " + fourViewedReleases[1].releaseName))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, fourViewedReleases[1].releaseName)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, fourViewedReleases[1].releaseId)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[1].artists + " - " + fourViewedReleases[1].releaseName)))) //Use an already displayed view to match the Carousel
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[2].artists + " - " + fourViewedReleases[2].releaseName))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, fourViewedReleases[2].releaseName)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, fourViewedReleases[2].releaseId)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[2].artists + " - " + fourViewedReleases[2].releaseName)))) //Use an already displayed view to match the Carousel
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(fourViewedReleases[3].artists + " - " + fourViewedReleases[3].releaseName))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, fourViewedReleases[3].releaseName)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, fourViewedReleases[3].releaseId)
    }

    @Test
    @Throws(InterruptedException::class)
    fun recommendations_intendCorrectly() {
        onView(withId(R.id.lytMainContent)).perform(swipeLeft())
        controller.controller.setRecommendations(recommendations)
        Thread.sleep(500)

        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(recommendations[0].title))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, recommendations[0].title)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, recommendations[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(recommendations[1].title))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, recommendations[1].title)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, recommendations[1].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(allOf(withClassName(`is`(Carousel::class.java.name)),
                hasDescendant(withText(recommendations[2].title))))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, recommendations[2].title)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, recommendations[2].id)
    }

    @Test
    @Throws(InterruptedException::class)
    fun orders_intendCorrectly() {
        onView(withId(R.id.lytMainContent)).perform(swipeLeft())
        controller.controller.setOrders(orders)
        Thread.sleep(500)

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("Buyer: " + orders[0].buyer.username)), click()))
        assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, orders[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("Buyer: " + orders[1].buyer.username)), click()))
        assertEquals((controller.router.getControllerWithTag("OrderController") as OrderController).id, orders[1].id)
    }

    @Test
    @Throws(InterruptedException::class)
    fun listings_intendCorrectly() {
        // Close navdrawer
        onView(withId(R.id.lytMainContent)).perform(swipeLeft())
        controller.controller.setSelling(listings)
        Thread.sleep(500)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(listings[0].getTitle())), click()))
        assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, listings[0].getTitle())
        assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, listings[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(listings[1].getTitle())), click()))
        assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, listings[1].getTitle())
        assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, listings[1].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(listings[2].getTitle())), click()))
        assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, listings[2].getTitle())
        assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, listings[2].id)
    }

//    @Test
//    fun overFiveItems_seeAllDisplayed() {
//
//    }

    private fun initialiseUi() {
        whenever(sharedPrefsManager.username).thenReturn("BjLairy")
        whenever(sharedPrefsManager.name).thenReturn("Joshy Boi")
        whenever(sharedPrefsManager.avatarUrl).thenReturn("http://thissomegoodshit.com")
        whenever(sharedPrefsManager.numCollection).thenReturn(numCollection)
        whenever(sharedPrefsManager.numWantlist).thenReturn(numWantlist)
        mActivityTestRule.runOnUiThread({
            controller.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(controller.activity as AppCompatActivity?, controller.router, controller.toolbar))
            controller.showLoading(false)
            controller.setupRecyclerView()
        })
        // TapTargetView
        onView(withId(R.id.search)).perform(click())
        onView(TestUtils.getHamburgerButton()).perform(click())
//        TestUtils.clickHomeButton()
    }
}