package bj.discogsbrowser;

import android.content.Context;

import com.github.scribejava.core.oauth.OAuth10aService;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.network.CacheProviders;
import bj.discogsbrowser.utils.AnalyticsTracker;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by j on 18/02/2017.
 */
@Component(modules = AppModule.class)
public interface AppComponent
{
    void inject(App app);

    Context getContext();

    Retrofit getRetrofit();

    CacheProviders getCacheProviders();

    DaoManager getDaoInteractor();

    OAuth10aService getOAuthService();

    AnalyticsTracker getAnalyticsTracker();
}
