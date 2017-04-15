package bj.rxjavaexperimentation;

import android.content.Context;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import bj.rxjavaexperimentation.network.DiscogsOAuthApi;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
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
    private Context mContext;

    public AppModule(Context context)
    {
        mContext = context;
    }

    @Provides
    Context provideContext()
    {
        return mContext;
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
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("oauth_consumer_key", context.getString(R.string.consumer_key))
                            .addQueryParameter("oauth_token", sharedPrefsManager.getUserOAuthToken().getToken())
                            .addQueryParameter("oauth_signature_method", "PLAINTEXT")
                            .addQueryParameter("oauth_timestamp", String.valueOf(System.currentTimeMillis()))
                            .addQueryParameter("oauth_nonce", "reroo")
                            .addQueryParameter("oauth_version", "1.0")
                            .addQueryParameter("oauth_signature", context.getString(R.string.consumer_secret) + "%26" + sharedPrefsManager.getUserOAuthToken().getTokenSecret())
                            .build();
                    request = request.newBuilder().url(url).build();
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
