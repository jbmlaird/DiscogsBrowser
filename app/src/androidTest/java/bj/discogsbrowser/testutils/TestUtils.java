package bj.discogsbrowser.testutils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by Josh Laird on 15/05/2017.
 */

public class TestUtils
{
    /**
     * Stub intents to target class.
     *
     * @param artistActivityClass Target class to stub intents to.
     */
    public static void stubIntents(Class artistActivityClass)
    {
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasComponent(artistActivityClass.getName())).respondWith(result);
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
}
