package bj.discogsbrowser.utils.analytics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import bj.discogsbrowser.BuildConfig;

/**
 * Created by Josh Laird on 04/05/2017.
 * <p>
 * Swallows events if the build is debug.
 */
public class AnalyticsTracker
{
    private Tracker tracker;

    public AnalyticsTracker(Tracker tracker)
    {
        this.tracker = tracker;
    }

    public void send(String screenName, String category, String action, String label, Long value)
    {
        if (!BuildConfig.DEBUG)
        {
            tracker.setScreenName(screenName);
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .setValue(value)
                    .build());
        }
    }
}
