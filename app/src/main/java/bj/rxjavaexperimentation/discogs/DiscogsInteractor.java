package bj.rxjavaexperimentation.discogs;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.EmptyObject;
import bj.rxjavaexperimentation.model.search.RootSearchResponse;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 18/02/2017.
 * <p>
 * Interactor to interact with the Discogs API.
 */
@Singleton
public class DiscogsInteractor
{
    private DiscogsService discogsService;
    private Context mContext;

    @Inject
    public DiscogsInteractor(Context context, Retrofit retrofit)
    {
        mContext = context;
        discogsService = retrofit.create(DiscogsService.class);
    }

    public Observable<Object> searchDiscogs(String searchTerm)
    {
        return discogsService.getSearchResults(searchTerm, mContext.getString(R.string.token))
                .subscribeOn(Schedulers.io())
                .flatMapIterable(RootSearchResponse::getSearchResults)
                .observeOn(Schedulers.io())
                .flatMap(searchResult ->
                {
                    switch (searchResult.getType())
                    {
                        case "release":
                            Log.e("DiscogsInteractor", "In release");
                            return discogsService.getRelease(searchResult.getId(), mContext.getString(R.string.token));
                        case "artist":
                            Log.e("DiscogsInteractor", "In artist");
                            return discogsService.getArtist(searchResult.getId(), mContext.getString(R.string.token));
                        default:
                            Log.e("DiscogsInteractor", "EmptyObject");
                            return new EmptyObject();
                    }
                });
    }
}
