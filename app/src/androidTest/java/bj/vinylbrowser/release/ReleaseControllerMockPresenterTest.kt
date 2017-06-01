package bj.vinylbrowser.release

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.label.LabelController
import bj.vinylbrowser.label.LabelPresenter
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.RouterAttacher
import bj.vinylbrowser.marketplace.MarketplaceController
import bj.vinylbrowser.marketplace.MarketplacePresenter
import bj.vinylbrowser.model.release.Release
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.utils.ImageViewAnimator
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

/**
 * Created by Josh Laird on 31/05/2017.
 */
class ReleaseControllerMockPresenterTest {
    @Rule @JvmField var rule = EspressoDaggerMockRule()
    @Rule @JvmField
    var mActivityTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java, false, false)
    val presenter: ReleasePresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val labelPresenter: LabelPresenter = mock()
    val marketplacePresenter: MarketplacePresenter = mock()
    val routerAttacher: RouterAttacher = mock()
    lateinit var epxController: ReleaseEpxController
    val releaseId = "releaseId"
    val releaseTitle = "releaseTitle"
    lateinit var controller: ReleaseController
    lateinit var release: Release
    val releaseListings = ScrapeListFactory.buildFourEmptyScrapeListing()

    @Before
    fun setUp() {
        release = ReleaseFactory.buildReleaseWithLabelNoneForSale("2")
        doAnswer { invocationOnMock -> invocationOnMock }.whenever(routerAttacher).attachRoot(any())
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).fetchReleaseDetails(any())
        doAnswer { invocation ->
            // Stub the call when LabelController is shown
            invocation
        }.whenever(labelPresenter).fetchReleaseDetails(any())
        doAnswer { invocation ->
            // Stub the call when MarketplaceController is shown
            invocation
        }.whenever(marketplacePresenter).getListingDetails(any())
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = ReleaseController(releaseTitle, releaseId)
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
            controller.controller.setRelease(release)
            Thread.sleep(100) //Sleep as you can't call two requestModelBuilds() simultaneously
            controller.controller.setReleaseListings(releaseListings)
        })
        epxController = controller.controller
    }

    @Test
    fun onClick_intentsLaunched() {
        onView(withText(release.title)).check(matches(isDisplayed()))
        onView(withText(release.artists[0].name)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(release.tracklist!![0].title))))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(release.tracklist!![1].title))))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(release.labels[0].name)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("LabelController") as LabelController).title, release.labels[0].name)
        Assert.assertEquals((controller.router.getControllerWithTag("LabelController") as LabelController).id, release.labels[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        // This will fail on emulators that don't have YouTube installed
        //        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(release.getVideos().get(0).getTitle())), click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(releaseListings[0].price)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, release.title)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, releaseListings[0].marketPlaceId)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(releaseListings[1].price)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).title, release.title)
        Assert.assertEquals((controller.router.getControllerWithTag("MarketplaceController") as MarketplaceController).id, releaseListings[1].marketPlaceId)
    }
}