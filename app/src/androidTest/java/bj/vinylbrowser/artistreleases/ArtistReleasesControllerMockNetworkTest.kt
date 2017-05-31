package bj.vinylbrowser.artistreleases

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.master.MasterController
import bj.vinylbrowser.master.MasterPresenter
import bj.vinylbrowser.model.artistrelease.ArtistRelease
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.release.ReleasePresenter
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.RecyclerViewSizeAssertion
import bj.vinylbrowser.testutils.TestActivity
import bj.vinylbrowser.testutils.TestUtils.withCustomConstraints
import bj.vinylbrowser.utils.ImageViewAnimator
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 * <p>
 * This doesn't use a mock Presenter as it uses a BehaviorRelay which passes data to the fragments.
 * <p>
 * Due to the Roboletric tests which test the models have been built, these Espresso tests test onClick and content.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class ArtistReleasesControllerMockNetworkTest {
    @Rule @JvmField val rule = EspressoDaggerMockRule()
    @Rule @JvmField
    val mActivityTestRule: IntentsTestRule<TestActivity> = IntentsTestRule(TestActivity::class.java, false, false)
    val imageViewAnimator: ImageViewAnimator = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val behaviorRelay: ArtistReleaseBehaviorRelay = mock()
    val masterPresenter: MasterPresenter = mock()
    val releasePresenter: ReleasePresenter = mock()
    lateinit var controller: ArtistReleasesController
    val artistId = "2089744"
    val artistTitle = "artistTitle"
    val releases = ArtistReleasesFactory.getTwoMastersTwoReleases()

    @Before
    fun setUp() {
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(masterPresenter).fetchReleaseDetails(any())
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(releasePresenter).fetchReleaseDetails(any())
        val artistReleaseRelay = BehaviorRelay.create<List<ArtistRelease>>()
        whenever(discogsInteractor.fetchArtistsReleases(artistId)).thenReturn(Single.just(releases))
        whenever(behaviorRelay.artistReleaseBehaviorRelay).thenReturn(artistReleaseRelay)
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = ArtistReleasesController(artistTitle, artistId)
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
        })
    }

    @Test
    fun loadData_viewPagerContainsCorrectData() {
        onView(withText("master1")).check(matches(isDisplayed()))
        onView(withText("master2")).check(matches(isDisplayed()))

        onView(withText("Releases")).perform(click())
        onView(withText("release1")).check(matches(isDisplayed()))
        onView(withText("release2")).check(matches(isDisplayed()))

        onView(withId(R.id.viewpager)).perform(withCustomConstraints(swipeRight(), isDisplayingAtLeast(80)))
        onView(withText("master1")).check(matches(isDisplayed()))
        onView(withText("master2")).check(matches(isDisplayed()))
    }

    @Test
    fun filterBoth_filtersCorrectly() {
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(RecyclerViewSizeAssertion(4)) // 4 as they will always have a SmallEmptySpaceModel_ top and bottom
        onView(withId(R.id.etFilter)).perform(typeText("master1"))
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(RecyclerViewSizeAssertion(3))
        onView(withId(R.id.etFilter)).perform(typeText("1"))
        onView(allOf(withText(controller.activity?.getString(R.string.no_items)), isDisplayed())).check(matches(isDisplayed())).perform(closeSoftKeyboard())

        onView(withId(R.id.viewpager)).perform(withCustomConstraints(swipeLeft(), isDisplayingAtLeast(50)))

        onView(allOf(withText(controller.activity?.getString(R.string.no_items)), isDisplayed())).check(matches(isDisplayed()))
        onView(withId(R.id.etFilter)).perform(clearText())
        onView(allOf(withId(R.id.recyclerView), isDisplayingAtLeast(51))).check(RecyclerViewSizeAssertion(4))
    }

    @Test
    fun onClick_launchesCorrectIntents() {
        // Start from 1 as first element is SmallEmptySpaceModel_
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).id, releases[0].id)
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).title, releases[0].title)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(allOf(withId(R.id.recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).id, releases[1].id)
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).title, releases[1].title)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(withId(R.id.viewpager)).perform(withCustomConstraints(swipeLeft(), isDisplayingAtLeast(51)))
        Thread.sleep(500)
        onView(allOf(withId(R.id.recyclerView), isDisplayingAtLeast(51))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, releases[2].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, releases[2].title)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

        onView(allOf(withId(R.id.recyclerView), isDisplayingAtLeast(51))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, releases[3].id)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, releases[3].title)
    }
}