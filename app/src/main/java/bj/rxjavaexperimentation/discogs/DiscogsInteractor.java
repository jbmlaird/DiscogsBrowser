package bj.rxjavaexperimentation.discogs;

import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.EmptyObject;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.discogs.gson.RootSearchResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interactor to interact with the Discogs API.
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

    public Observable<Object> searchDiscogs(String searchTerm)
    {
        return discogsService.getSearchResults(searchTerm, mContext.getString(R.string.token))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMapIterable(RootSearchResponse::getSearchResults)
                .observeOn(Schedulers.io())
                .flatMap(searchResult ->
                {
                    if (searchResult.getType().equals("release"))
                    {
                        Log.e("DiscogsInteractor", "In release");
                        return discogsService.getRelease(searchResult.getId(), mContext.getString(R.string.token));
                    }
                    else if (searchResult.getType().equals("artist"))
                    {
                        Log.e("DiscogsInteractor", "In artist");
                        return discogsService.getArtist(searchResult.getId(), mContext.getString(R.string.token));
                    }
                    Log.e("DiscogsInteractor", "EmptyObject");
                    return new EmptyObject();
                });
    }
}
