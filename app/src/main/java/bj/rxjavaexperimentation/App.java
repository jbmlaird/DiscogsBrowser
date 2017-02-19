package bj.rxjavaexperimentation;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
        setupUniversalImageLoader();
    }

    /**
     * Sets up the Universal Image Loader library.
     */
    private void setupUniversalImageLoader()
    {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void setupGraph()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
