package bj.rxjavaexperimentation.search;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.discogs.DiscogsInteractor;
import dagger.Module;
import dagger.Provides;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Josh Laird on 20/02/2017.
 */

@Module
public class SearchModule
{
    private static final String TAG = "SearchModule";
    private SearchContract.View mView;

    public SearchModule(SearchContract.View view)
    {
        mView = view;
    }

    @Provides
    SearchContract.View providesSearchView()
    {
        return mView;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Context context)
    {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.discogs_base))
                .build();
    }

    @Provides
    @Singleton
    Function<SearchViewQueryTextEvent, ObservableSource<?>> providesSearchFunction(DiscogsInteractor discogsInteractor)
    {
        return searchViewQueryTextEvent -> discogsInteractor.searchDiscogs(searchViewQueryTextEvent.queryText().toString()).startWith("");
    }
}
