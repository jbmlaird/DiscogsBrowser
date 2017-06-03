package bj.vinylbrowser.marketplace

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.listing.ListingFactory
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.RouterAttacher
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.TestUtils
import bj.vinylbrowser.userdetails.UserDetailsFactory
import bj.vinylbrowser.utils.ImageViewAnimator
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers.`is`
import org.hamcrest.core.IsNot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class MarketplaceControllerMockPresenterTest {
    @Rule @JvmField val rule = EspressoDaggerMockRule()
    @Rule @JvmField
    val mActivityTestRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java, false, false)
    val presenter: MarketplacePresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val routerAttacher: RouterAttacher = mock()
    val listingTitle = "listingTitle"
    val listingId = "listingId"
    val listing = ListingFactory.buildListing("")
    val testUserDetails = UserDetailsFactory.buildUserDetails()
    lateinit var controller: MarketplaceController
    lateinit var epxController: MarketplaceEpxController

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
        }.whenever(presenter).getListingDetails(listingId)
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = MarketplaceController(listingTitle, listingId, "", "")
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
            controller.epxController.setListing(listing)
            Thread.sleep(100) //Sleep as you can't call two requestModelBuilds() simultaneously
            controller.epxController.setSellerDetails(testUserDetails)
        })
        epxController = controller.epxController
    }

    @Test
    @Throws(InterruptedException::class)
    fun onLoad_displaysCorrectInfo() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(listing.comments))))
        onView(withText(listing.condition)).check(matches(isDisplayed()))
        onView(withText(listing.sleeveCondition)).check(matches(isDisplayed()))
        onView(withText(listing.seller.username)).check(matches(isDisplayed()))
        onView(withText(testUserDetails.sellerRating.toString() + "%")).check(matches(isDisplayed()))
    }

    @Test
    fun buttonsClicked_intentsLaunched() {
        TestUtils.stubIntentAction(Intent.ACTION_VIEW)

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(epxController.adapter.itemCount - 1))

        onView(withText("View seller shipping info")).perform(click())
        onView(withText(listing.seller.username))
                .inRoot(withDecorView(IsNot.not(`is`(mActivityTestRule.activity.window.decorView)))).check(matches(isDisplayed()))
        onView(withText("Dismiss"))
                .inRoot(withDecorView(IsNot.not(`is`(mActivityTestRule.activity.window.decorView)))).perform(click())
        onView(withText("View on Discogs")).perform(click())
        intended(hasAction(Intent.ACTION_VIEW))
    }
}