package bj.vinylbrowser.testutils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.hamcrest.Matcher;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Josh Laird on 15/05/2017.
 */

public class TestUtils
{
    /**
     * Stub intents to target class.
     *
     * @param classToStub Target class to stub intents to.
     */
    public static void stubIntentClass(Class classToStub)
    {
        intending(hasComponent(classToStub.getName())).respondWith(buildOkResult());
    }

    /**
     * Stub intents by Action. Currently only used for ACTION_VIEW (WebViews)
     *
     * @param actionView Action
     */
    public static void stubIntentAction(String actionView)
    {
        intending(hasAction(actionView)).respondWith(buildOkResult());
    }

    private static Instrumentation.ActivityResult buildOkResult()
    {
        Intent resultData = new Intent();
        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }

    /**
     * From: http://stackoverflow.com/questions/33505953/espresso-how-to-test-swiperefreshlayout/33516360#33516360
     * <p>
     * ViewPager will throw an error if not 90% of it is displayed. This reduces the restriction.
     *
     * @param action      Action to be done.
     * @param constraints Restrictions on the view.
     * @return Performs an action on the view.
     */
    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints)
    {
        return new ViewAction()
        {
            @Override
            public Matcher<View> getConstraints()
            {
                return constraints;
            }

            @Override
            public String getDescription()
            {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view)
            {
                action.perform(uiController, view);
            }
        };
    }

    /**
     * A matcher to match EditText text contents.
     */
    public static class isEditTextEqualTo implements ViewAssertion
    {
        private String searchTerm = "";

        public isEditTextEqualTo(String searchTerm)
        {
            this.searchTerm = searchTerm;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException)
        {
            if (noViewFoundException != null)
                throw noViewFoundException;

            assertEquals(((EditText) view).getText().toString(), searchTerm);
        }
    }

    /**
     * A matcher to get the ActionBar home/hamburger button.
     * <p>
     * Taken from: http://stackoverflow.com/a/34658817/4624156
     *
     * @return Matcher.
     */
    public static Matcher<View> getHomeButton()
    {
        return allOf(
                withParent(withClassName(is(Toolbar.class.getName()))),
                withClassName(anyOf(is(ImageButton.class.getName()),
                        is(AppCompatImageButton.class.getName()))));
    }
}
