package bj.vinylbrowser;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.bugsnag.android.Bugsnag;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.squareup.leakcanary.LeakCanary;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import bj.vinylbrowser.utils.NavigationDrawerBuilder;
import bj.vinylbrowser.utils.analytics.AnalyticsModule;
import bj.vinylbrowser.wrappers.WrappersModule;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by j on 18/02/2017.
 */
public class App extends Application
{
    public static AppComponent appComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this))
        {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        setupGraph();
        Iconify.with(new FontAwesomeModule());
        if (!BuildConfig.DEBUG)
            Bugsnag.init(this);
        else
            LeakCanary.install(this);

        // Empty string while RxSocialConnect's disk cache is not working
        RxSocialConnect.register(this, "")
                .using(new GsonSpeaker());
        NavigationDrawerBuilder.initialiseMaterialDrawerImageLoader();
    }

    private void setupGraph()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .analyticsModule(new AnalyticsModule())
                .wrappersModule(new WrappersModule())
                .build();
        appComponent.inject(this);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setComponent(AppComponent component)
    {
        appComponent = component;
    }
}
