package bj.rxjavaexperimentation;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.squareup.leakcanary.LeakCanary;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

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
        LeakCanary.install(this);
        setupGraph();
        Iconify
                .with(new FontAwesomeModule());

        // Empty string while RxSocialConnect's disk cache is not working
        RxSocialConnect.register(this, "testString")
                .using(new GsonSpeaker());
    }

    private void setupGraph()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
