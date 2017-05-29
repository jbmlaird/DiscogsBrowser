package bj.vinylbrowser.login;


import android.support.test.espresso.intent.Intents;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import bj.vinylbrowser.R;
import bj.vinylbrowser.first.FirstActivity;
import bj.vinylbrowser.testutils.EspressoDaggerMockRule;
import bj.vinylbrowser.testutils.TestUtils;
import bj.vinylbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityMockPresenterTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class, false, false);

    @Mock LoginPresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;

    @Before
    public void setUp()
    {
        doAnswer(invocation ->
                // Disable spinning to not cause Espresso timeout
                invocation).when(imageViewAnimator).rotateImage(any());
    }

    @Test
    public void clickPrivacyPolicy_displaysDismisses() throws InterruptedException
    {
        mActivityTestRule.launchActivity(null);

        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTnCs)).check(matches(isDisplayed())).perform(click());
        onView(withText("Privacy Policy"))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))));
        onView(withText("Dismiss"))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).perform(click());
    }

    @Test
    public void onBackPressedDialog_closes() throws Throwable
    {
        mActivityTestRule.launchActivity(null);

        mActivityTestRule.runOnUiThread(() -> mActivityTestRule.getActivity().onBackPressed());
        Thread.sleep(500);

        onView(withResourceName("md_title")).check(matches(isDisplayed()));
        onView(withResourceName("md_buttonDefaultPositive")).perform(click());
        assertTrue(mActivityTestRule.getActivity().isFinishing());
    }

    @Test
    public void userLogsIn_finishes() throws InterruptedException
    {
        Intents.init();
        doAnswer(invocation ->
        {
            mActivityTestRule.getActivity().finishActivityLaunchMain();
            return invocation;
        }).when(presenter).beginLogin();
        TestUtils.stubIntentClass(FirstActivity.class);
        mActivityTestRule.launchActivity(null);

        onView(withId(R.id.btnLogin)).perform(click());

        assertTrue(mActivityTestRule.getActivity().isFinishing());
        intended(hasComponent(FirstActivity.class.getName()));
        Intents.release();
    }
}
