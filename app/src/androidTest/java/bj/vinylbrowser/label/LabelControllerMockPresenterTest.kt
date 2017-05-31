package bj.vinylbrowser.label

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.*
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.model.common.Label
import bj.vinylbrowser.model.labelrelease.LabelRelease
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.release.ReleaseFactory
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.TestActivity
import bj.vinylbrowser.testutils.TestUtils
import bj.vinylbrowser.utils.ImageViewAnimator
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class LabelControllerMockPresenterTest {
    @Rule @JvmField var rule = EspressoDaggerMockRule()
    @Rule @JvmField
    var mActivityTestRule = IntentsTestRule<TestActivity>(TestActivity::class.java, false, false)
    val presenter: LabelPresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    lateinit var controller: LabelController
    lateinit var epxController: LabelEpxController
    lateinit var testLabel: Label
    lateinit var labelRelease: LabelRelease
    private val title = "labelTitle"
    private val id = "labelId"

    @Before
    fun setUp() {
        labelRelease = LabelFactory.buildLabelRelease(1)
        testLabel = ReleaseFactory.buildReleaseLabel(id)

        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        doAnswer { invocation ->
            // Swallow
            invocation
        }.whenever(presenter).fetchReleaseDetails(id)
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = LabelController(title, id)
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
            controller.controller.setLabel(testLabel)
            Thread.sleep(100) //Sleep as you can't call two requestModelBuilds() simultaneously
            controller.controller.setLabelReleases(listOf(labelRelease))
        })
        epxController = controller.controller
    }

    @Test
    @Throws(InterruptedException::class)
    fun onLoad_displaysCorrectData() {
        onView(withId(R.id.recyclerView))
                // Scroll to bottom as that's where the label info is
                .perform(scrollToPosition<RecyclerView.ViewHolder>(controller.recyclerView.adapter.itemCount - 1))
        onView(withText(testLabel.name)).check(matches(isDisplayed()))
        onView(withText(testLabel.profile)).check(matches(isDisplayed()))
        onView(withText(labelRelease.title + " (" + labelRelease.catno + ")")).check(matches(isDisplayed()))
        onView(withText(labelRelease.artist)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun onLabelReleaseClicked_launchesReleaseActivity() {
        onView(withId(R.id.recyclerView))
                // 0: Header, 1: Divider, 2: Release
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, labelRelease.title)
        assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, labelRelease.id)
    }

    @Test
    @Throws(InterruptedException::class)
    fun onViewOnDiscogsClicked_launchesWebView() {
        TestUtils.stubIntentAction(Intent.ACTION_VIEW)

        onView(withId(R.id.recyclerView))
                .perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("View on Discogs")), click()))

        intended(hasAction(Intent.ACTION_VIEW))
    }
}