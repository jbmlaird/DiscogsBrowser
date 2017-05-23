package bj.vinylbrowser.utils.analytics;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Singleton;

import bj.vinylbrowser.R;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 13/05/2017.
 */
@Module
public class AnalyticsModule
{
    @Provides
    @Singleton
    protected Tracker provideTracker(Context context)
    {
        return GoogleAnalytics.getInstance(context).newTracker(R.xml.global_tracker);
    }

    @Provides
    @Singleton
    protected AnalyticsTracker provideAnalyticsTracker(Tracker tracker)
    {
        return new AnalyticsTracker(tracker);
    }
}
