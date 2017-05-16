package bj.discogsbrowser.testutils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matcher;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static junit.framework.Assert.assertEquals;

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
    public static void stubIntents(Class classToStub)
    {
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasComponent(classToStub.getName())).respondWith(result);
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
}
