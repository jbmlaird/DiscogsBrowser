package bj.vinylbrowser.master

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.RouterAttacher
import bj.vinylbrowser.release.ReleaseController
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
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class MasterControllerMockPresenterTest {
    @Rule @JvmField var rule = EspressoDaggerMockRule()
    @Rule @JvmField
    var mActivityTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java, false, false)
    val presenter: MasterPresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val routerAttacher: RouterAttacher = mock()
    val masterTitle = "masterTitle"
    val masterId = "masterId"
    lateinit var controller: MasterController
    lateinit var epxController: MasterEpxController
    val master = MasterFactory.buildMaster()
    val masterMasterVersions = MasterVersionsFactory.buildMasterVersions(2)

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
        }.whenever(presenter).fetchReleaseDetails(masterId)
        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = MasterController(masterTitle, masterId)
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
            Thread.sleep(500)
            controller.epxController.setMaster(master)
            Thread.sleep(100) //Sleep as you can't call two requestModelBuilds() simultaneously
            controller.epxController.setMasterVersions(masterMasterVersions)
        })
        epxController = controller.epxController
    }

    @Test
    @Throws(InterruptedException::class)
    fun onLoad_displaysCorrectData() {
        onView(withId(R.id.recyclerView))
                // Scroll to divider
                .perform(scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withText(master.title)).check(matches(isDisplayed()))
        onView(withText(master.artists[0].name)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView))
                // Scroll to end
                .perform(scrollToPosition<RecyclerView.ViewHolder>(epxController.adapter.itemCount - 1))
        onView(withText(masterMasterVersions[0].title)).check(matches(isDisplayed()))
        onView(withText(masterMasterVersions[1].title)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun onClick_launchesIntents() {
        onView(withId(R.id.recyclerView))
                // Scroll to master version
                .perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(masterMasterVersions[0].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, masterMasterVersions[0].title)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, masterMasterVersions[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }
        Thread.sleep(500)

        onView(withId(R.id.recyclerView))
                // Scroll to master version
                .perform(actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(masterMasterVersions[1].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, masterMasterVersions[1].title)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, masterMasterVersions[1].id)
    }
}