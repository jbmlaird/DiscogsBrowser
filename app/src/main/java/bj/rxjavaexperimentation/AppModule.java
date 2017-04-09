package bj.rxjavaexperimentation;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
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
    Retrofit providesRetrofit(Context context)
    {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.discogs_base))
                .build();
    }
}
