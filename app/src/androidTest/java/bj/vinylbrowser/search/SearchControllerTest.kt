package bj.vinylbrowser.search

import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.R
import bj.vinylbrowser.artist.ArtistController
import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.greendao.SearchTerm
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.main.RouterAttacher
import bj.vinylbrowser.master.MasterController
import bj.vinylbrowser.model.search.SearchResult
import bj.vinylbrowser.release.ReleaseController
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.TestUtils
import bj.vinylbrowser.utils.ImageViewAnimator
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * Created by Josh Laird on 31/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class SearchControllerTest {
    @Rule @JvmField var rule = EspressoDaggerMockRule()
    @Rule @JvmField
    var mActivityTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java, false, false)
    val imageViewAnimator: ImageViewAnimator = mock()
    val daoManager: DaoManager = mock()
    val routerAttacher: RouterAttacher = mock()
    val searchFunction: Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> = mock()
    val results = SearchResultFactory.getOneArtistTwoMastersThreeReleases()
    lateinit var controller: SearchController
    lateinit var epxController: SearchEpxController
    val searchQuery = "yeeeeboi"
    val searchTermText = "search term"

    @Before
    fun setUp() {
        doAnswer { invocationOnMock -> invocationOnMock }.whenever(routerAttacher).attachRoot(any())
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
        val searchTerm = SearchTerm()
        searchTerm.searchTerm = searchTermText
        whenever(daoManager.recentSearchTerms).thenReturn(listOf(searchTerm))
        whenever(searchFunction.apply(any())).thenReturn(Observable.just(results).delay(500, TimeUnit.MILLISECONDS))

        mActivityTestRule.launchActivity(null)
        mActivityTestRule.runOnUiThread({
            controller = SearchController()
            controller.retainViewMode = Controller.RetainViewMode.RETAIN_DETACH
            mActivityTestRule.activity.router.pushController(RouterTransaction.with(controller))
        })
        epxController = controller.controller
    }

    @Test
    fun clickingRecentSearch_fillsBox() {
        onView(withText(searchTermText)).perform(click())
        onView(withId(R.id.search_src_text)).check(TestUtils.isEditTextEqualTo(searchTermText))
        assertEquals(epxController.adapter.itemCount, 1)
    }

    @Test
    @Throws(Exception::class)
    fun filtersCorrectly() {
        onView(withId(R.id.search_src_text)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText(searchQuery))
        closeSoftKeyboard()
        Thread.sleep(1001) // a bit over debounce time - could make this a static variable and set to zero
        assertEquals(epxController.adapter.itemCount, results.size)
        onView(withText("Artist")).perform(click())
        onView(withText(results[0].title)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withText("Master")).perform(click())
        onView(withText(results[1].title)).check(matches(isDisplayed()))
        onView(withText(results[2].title)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withText("Release")).perform(click())
        onView(withText(results[3].title)).check(matches(isDisplayed()))
        onView(withText(results[4].title)).check(matches(isDisplayed()))
        onView(withText(results[5].title)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withText("Label")).perform(click())
        onView(withText("No search results")).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun clickList_intendsCorrectly() {
        onView(withId(R.id.search_src_text)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText(searchQuery))
        closeSoftKeyboard()
        Thread.sleep(1501)
        assertEquals(epxController.adapter.itemCount, results.size)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(results[0].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ArtistController") as ArtistController).title, results[0].title)
        Assert.assertEquals((controller.router.getControllerWithTag("ArtistController") as ArtistController).id, results[0].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

//        epxController.setSearchResults(results) // saveViewState doesn't seem to work in this test
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(results[1].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).title, results[1].title)
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).id, results[1].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

//        epxController.setSearchResults(results)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(results[2].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).title, results[2].title)
        Assert.assertEquals((controller.router.getControllerWithTag("MasterController") as MasterController).id, results[2].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

//        epxController.setSearchResults(results)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(results[3].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, results[3].title)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, results[3].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

//        epxController.setSearchResults(results)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(results[4].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, results[4].title)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, results[4].id)
        mActivityTestRule.runOnUiThread { controller.router.popCurrentController() }

//        epxController.setSearchResults(results)
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(results[5].title)), click()))
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).title, results[5].title)
        Assert.assertEquals((controller.router.getControllerWithTag("ReleaseController") as ReleaseController).id, results[5].id)
    }
}