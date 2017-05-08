package bj.discogsbrowser;

import android.app.Application;
import android.content.Context;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.greendao.database.Database;

import bj.discogsbrowser.greendao.DaoMaster;
import bj.discogsbrowser.greendao.DaoSession;
import bj.discogsbrowser.network.DiscogsOAuthApi;
import bj.discogsbrowser.utils.DaoInteractor;
import bj.discogsbrowser.utils.SharedPrefsManager;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j on 18/02/2017.
 */
@Module
public class AppModule
{
    private Application applicationContext;

    public AppModule(Application context)
    {
        applicationContext = context;
    }

    @Provides
    Context provideContext()
    {
        return applicationContext;
    }

    @Provides
    Tracker provideAnalyticsTracker()
    {
        return GoogleAnalytics.getInstance(applicationContext).newTracker(R.xml.global_tracker);
    }

    @Provides
    DaoSession providesDaoSession()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(applicationContext, "search-db");
        // Use when changing schema
        // helper.onUpgrade(helper.getWritableDatabase(), 7, 8);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Provides
    DaoInteractor providesDaoInteractor(DaoSession daoSession)
    {
        return new DaoInteractor(daoSession);
    }

    @Provides
    OAuth10aService providesOAuth1Service(Context context)
    {
        return new ServiceBuilder()
                .apiKey(context.getString(R.string.consumer_key))
                .apiSecret(context.getString(R.string.consumer_secret))
                .callback("http://reroo.co.uk")
                .build(DiscogsOAuthApi.instance());
    }

    @Provides
    Retrofit providesRetrofit(Context context, SharedPrefsManager sharedPrefsManager)
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain ->
                {
                    Request request = chain.request();
                    Request build = request.newBuilder().addHeader("User-Agent", "DiscogsBrowser/0.1").build();
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
}
