package bj.rxjavaexperimentation.main;

import android.content.Context;
import android.util.Log;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.discogs.DiscogsInteractor;
import bj.rxjavaexperimentation.gson.Result;
import bj.rxjavaexperimentation.gson.RootResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by j on 18/02/2017.
 */

public class MainPresenter implements MainContract.Presenter, FloatingSearchView.OnSearchListener
{
    private static final String TAG = "MainPresenter";
    private final Context mContext;
    private MainContract.View mView;
    private DiscogsInteractor mInteractor;

    @Inject
    public MainPresenter(Context context, DiscogsInteractor interactor)
    {
        mContext = context;
        mInteractor = interactor;
    }

    @Override
    public void setView(MainContract.View view)
    {
        mView = view;
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion)
    {

    }

    @Override
    public void onSearchAction(String currentQuery)
    {
        mInteractor.searchDiscogs(currentQuery)
                .map(new Function<RootResponse, List<Result>>()
                {
                    @Override
                    public List<Result> apply(RootResponse rootResponse) throws Exception
                    {
                        return rootResponse.getResults();
                    }
                }).subscribe(new Observer<List<Result>>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {

            }

            @Override
            public void onNext(List<Result> value)
            {
                Log.e(TAG, "Success! " + value);
                mView.setSuccessText("Success! " + value.size());
            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(TAG, "Error: " + e.toString());
                Log.e(TAG, "Error: " + e.toString());
            }

            @Override
            public void onComplete()
            {

            }
        });
    }
}
