package bj.rxjavaexperimentation.discogs;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.gson.RootResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Josh Laird on 18/02/2017.
 */
@Singleton
public class DiscogsInteractor
{
    private Retrofit retrofit;
    private DiscogsService discogsService;
    private Context mContext;

    @Inject
    public DiscogsInteractor(Context context)
    {
        mContext = context;
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.discogs_base))
                .build();

        discogsService = retrofit.create(DiscogsService.class);
    }

    public Observable<RootResponse> searchDiscogs(String searchTerm)
    {
        return discogsService.getSearchResults(searchTerm, mContext.getString(R.string.token)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
}
