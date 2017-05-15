package bj.discogsbrowser.testutils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

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
}
