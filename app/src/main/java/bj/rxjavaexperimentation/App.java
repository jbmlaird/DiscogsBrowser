package bj.rxjavaexperimentation;

import android.app.Application;

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
        setupGraph();
    }

    private void setupGraph()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
