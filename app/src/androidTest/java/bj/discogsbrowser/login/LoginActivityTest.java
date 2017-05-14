package bj.discogsbrowser.login;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import bj.discogsbrowser.EspressoDaggerMockRule;
import bj.discogsbrowser.R;
import bj.discogsbrowser.main.MainActivity;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest
{
    @Rule public EspressoDaggerMockRule rule = new EspressoDaggerMockRule();
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class, false, false);

    @Mock LoginPresenter presenter;
    @Mock ImageViewAnimator imageViewAnimator;
    private Instrumentation.ActivityResult result;

    @Before
    public void setUp()
    {
        Intent resultData = new Intent();
        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

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
    public void userLogsIn_finishes() throws InterruptedException
    {
        Intents.init();
        when(presenter.hasUserLoggedIn()).thenReturn(false);
        doAnswer(invocation ->
        {
            mActivityTestRule.getActivity().finish();
            return invocation;
        }).when(presenter).startOAuthService(any());
        // Stub all MainActivity intents
        intending(hasComponent(MainActivity.class.getName())).respondWith(result);

        mActivityTestRule.launchActivity(null);
        onView(withId(R.id.btnLogin)).perform(click());

        assertTrue(mActivityTestRule.getActivity().isFinishing());
        intended(
                hasComponent(MainActivity.class.getName()));
        Intents.release();
    }
}
