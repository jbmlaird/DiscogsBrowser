package bj.vinylbrowser.login

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import bj.vinylbrowser.R
import bj.vinylbrowser.main.MainActivity
import bj.vinylbrowser.testutils.EspressoDaggerMockRule
import bj.vinylbrowser.testutils.TestUtils
import bj.vinylbrowser.utils.ImageViewAnimator
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Josh Laird on 31/05/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class LoginActivityMockPresenterTest {
    @Rule @JvmField var rule = EspressoDaggerMockRule()
    @Rule @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java, false, false)

    val presenter: LoginPresenter = mock()
    val imageViewAnimator: ImageViewAnimator = mock()

    @Before
    fun setUp() {
        doAnswer { invocation ->
            // Disable spinning to not cause Espresso timeout
            invocation
        }.whenever(imageViewAnimator).rotateImage(any())
    }

    @Test
    @Throws(InterruptedException::class)
    fun clickPrivacyPolicy_displaysDismisses() {
        mActivityTestRule.launchActivity(null)

        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTnCs)).check(matches(isDisplayed())).perform(click())
        onView(withText("Privacy Policy"))
                .inRoot(withDecorView(not(`is`(mActivityTestRule.activity.window.decorView))))
        onView(withText("Dismiss"))
                .inRoot(withDecorView(not(`is`(mActivityTestRule.activity.window.decorView)))).perform(click())
    }

    @Test
    @Throws(Throwable::class)
    fun onBackPressedDialog_closes() {
        mActivityTestRule.launchActivity(null)

        mActivityTestRule.runOnUiThread { mActivityTestRule.activity.onBackPressed() }
        Thread.sleep(500)

        onView(withResourceName("md_title")).check(matches(isDisplayed()))
        onView(withResourceName("md_buttonDefaultPositive")).perform(click())
        assertTrue(mActivityTestRule.activity.isFinishing)
    }

    @Test
    @Throws(InterruptedException::class)
    fun userLogsIn_finishes() {
        Intents.init()
        doAnswer { invocation ->
            mActivityTestRule.activity.finishActivityLaunchMain()
            invocation
        }.whenever(presenter).beginLogin()
        TestUtils.stubIntentClass(MainActivity::class.java)
        mActivityTestRule.launchActivity(null)

        onView(withId(R.id.btnLogin)).perform(click())

        assertTrue(mActivityTestRule.activity.isFinishing)
        intended(hasComponent(MainActivity::class.java.name))
        Intents.release()
    }
}