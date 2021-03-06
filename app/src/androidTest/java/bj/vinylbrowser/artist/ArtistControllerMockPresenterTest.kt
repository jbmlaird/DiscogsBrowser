package bj.vinylbrowser.artist

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.times
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.artistreleases.ArtistReleasesPresenter
import bj.vinylbrowser.artistreleases.ArtistResultFactory
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.RouterAttacher
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.TestUtils
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.SharedPrefsManager
import com.bluelinelabs.conductor.RouterTransaction
import com.nhaarman.mockito_kotlin.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 30/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class ArtistControllerMockPresenterTest {
    @Rule
    @JvmField
    val rule = EspressoDaggerMockRule()
    @Rule
    @JvmField
    val mActivityTestRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java, false, false)
    val imageViewAnimator: ImageViewAnimator = mock()
    val routerAttacher: RouterAttacher = mock()
    val artistPresenter: ArtistPresenter = mock()
    val artistReleasesPresenter: ArtistReleasesPresenter = mock()
    val sharedPrefsManager: SharedPrefsManager = mock()
    lateinit var controller: ArtistController
    val artistResult = ArtistResultFactory.buildArtistResult(2)
    val artistId = "artistId"
    val artistTitle = "artistTitle"

    @Before
    fun setUp() {
        doAnswer { invocationOnMock -> invocationOnMock }.whenever(routerAttacher).attachRoot(any())
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // swallow
            invocation
        }.whenever(artistPresenter).fetchReleaseDetails(any())
        whenever(sharedPrefsManager.isUserLoggedIn).thenReturn(true)

        mActivityTestRule.launchActivity(null)
        Thread.sleep(1000)
        mActivityTestRule.runOnUiThread({
            controller = ArtistController(artistId, artistTitle)
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
        })
    }

    @Test
    fun onLaunch_allInfoDisplayed() {
        controller.epxController.setArtist(artistResult)

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(artistResult.nameVariations[0]))))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(artistResult.members[0].name))))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(artistResult.members[1].name))))

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(artistResult.artistWantedUrls[0].displayText))))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(mActivityTestRule.activity.getString(R.string.view_releases)))))
    }

    @Test
    fun membersClick_launchesIntent() {
        controller.epxController.setArtist(artistResult)

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(artistResult.members[0].name)), click()))
        Thread.sleep(500)
        onView(allOf(withId(R.id.recyclerView), hasDescendant(withText(artistResult.members[0].name)), isCompletelyDisplayed())).check(matches(isDisplayed()))
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(artistResult.members[1].name)), click()))
        Thread.sleep(500)
        onView(allOf(withId(R.id.recyclerView), hasDescendant(withText(artistResult.members[1].name)), isCompletelyDisplayed())).check(matches(isDisplayed()))
        verify(artistPresenter).fetchReleaseDetails(artistResult.members[0].id) // Verifying that
        // a method in another Activity was called because you can't stub intents to other fragments
        verify(artistPresenter).fetchReleaseDetails(artistResult.members[1].id)
    }

    @Test
    fun viewReleasesClicked_launchesIntent() {
        doAnswer { invocation ->
            // swallow
            invocation
        }.whenever(artistReleasesPresenter).fetchArtistReleases(any<String>())

        controller.epxController.setArtist(artistResult)

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(controller.activity?.getString(R.string.view_releases))), click()))

        verify(artistReleasesPresenter).fetchArtistReleases(artistResult.id)
    }

    @Test
    fun linksClicked_launchesWebView() {
        TestUtils.stubIntentAction(Intent.ACTION_VIEW)
        controller.epxController.setArtist(artistResult)

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("spotify")), click()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("redtube")), click()))

        intended(hasAction(Intent.ACTION_VIEW), times(2))
    }
}