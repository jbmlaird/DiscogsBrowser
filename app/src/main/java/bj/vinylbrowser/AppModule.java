package bj.vinylbrowser;

import android.app.Application;
import android.content.Context;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import bj.vinylbrowser.network.CacheProviders;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.network.DiscogsOAuthApi;
import bj.vinylbrowser.utils.DiscogsScraper;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j on 18/02/2017.
 * <p>
 * Protected @Provides for DaggerMock library.
 */
@Module
public class AppModule
{
    private Application applicationContext;
    private CacheProviders cacheProviders;

    public AppModule(Application context)
    {
        applicationContext = context;
        cacheProviders = new RxCache.Builder()
                .setMaxMBPersistenceCache(5)
                .persistence(context.getFilesDir(), new GsonSpeaker())
                .using(CacheProviders.class);
    }

    @Provides
    @Singleton
    protected Context provideContext()
    {
        return applicationContext;
    }

    @Provides
    protected MySchedulerProvider mySchedulerProvider()
    {
        return new MySchedulerProvider();
    }

    @Provides
    @Singleton
    protected OAuth10aService providesOAuth1Service(Context context)
    {
        return new ServiceBuilder()
                .apiKey(context.getString(R.string.consumer_key))
                .apiSecret(context.getString(R.string.consumer_secret))
                .callback("http://reroo.co.uk")
                .build(DiscogsOAuthApi.instance());
    }

    @Provides
    protected SharedPrefsManager sharedPrefsManager(Context context)
    {
        return new SharedPrefsManager(context);
    }

    @Provides
    @Singleton
    protected Retrofit providesRetrofit(Context context, SharedPrefsManager sharedPrefsManager)
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain ->
                {
                    Request request = chain.request();
                    Request build = request.newBuilder().addHeader("User-Agent", "VinylBrowser/0.1").build();
                    HttpUrl url = build.url().newBuilder()
                            .addQueryParameter("oauth_consumer_key", context.getString(R.string.consumer_key))
                            .addQueryParameter("oauth_token", sharedPrefsManager.getUserOAuthToken().getToken())
                            .addQueryParameter("oauth_signature_method", "PLAINTEXT")
                            .addQueryParameter("oauth_timestamp", String.valueOf(System.currentTimeMillis()))
                            .addQueryParameter("oauth_nonce", "reroo")
                            .addQueryParameter("oauth_version", "1.0")
                            .addQueryParameter("oauth_signature", context.getString(R.string.consumer_secret) + "%26" + sharedPrefsManager.getUserOAuthToken().getTokenSecret())
                            .build();
                    request = build.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.discogs_base))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    protected CacheProviders provideCacheProviders()
    {
        return cacheProviders;
    }

    @Provides
    protected DiscogsInteractor provideDiscogsInteractor(Retrofit retrofit, MySchedulerProvider mySchedulerProvider, DiscogsScraper scraper, SharedPrefsManager sharedPrefsManager)
    {
        return new DiscogsInteractor(applicationContext, retrofit, mySchedulerProvider, scraper, sharedPrefsManager);
    }
}
