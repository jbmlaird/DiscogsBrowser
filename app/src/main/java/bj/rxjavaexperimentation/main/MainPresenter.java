package bj.rxjavaexperimentation.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.rxjavaexperimentation.discogs.DiscogsInteractor;
import bj.rxjavaexperimentation.discogs.gson.release.Release;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by j on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter, FloatingSearchView.OnSearchListener
{
    private static final String TAG = "MainPresenter";
    private Context mContext;
    private MainContract.View mView;
    private DiscogsInteractor mInteractor;
    private RecyclerViewResultsAdapter recyclerViewResultsAdapter;
    private ArrayList<Release> results = new ArrayList<>();

    @Inject
    public MainPresenter(DiscogsInteractor interactor)
    {
        mInteractor = interactor;
    }

    @Override
    public void setView(MainContract.View view)
    {
        mContext = view.getActivity();
        mView = view;
    }

    /**
     * Necessary override in case the user clicks a suggestion provided by {{@link FloatingSearchView}}.
     *
     * @param searchSuggestion
     */
    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion)
    {

    }

    @Override
    public void setupRecyclerView(RecyclerView rvResults)
    {
        rvResults.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewResultsAdapter = new RecyclerViewResultsAdapter(mContext, results);
        rvResults.setAdapter(recyclerViewResultsAdapter);
    }

    /**
     * Called when the user presses enter on the {@link FloatingSearchView}.
     *
     * @param query String to search for.
     */
    @Override
    public void onSearchAction(String query)
    {
        results.clear();
        mInteractor.searchDiscogs(query, this);
        mView.showProgressBar();
    }

    @Override
    public void addToRecyclerView(Observable<Release> value)
    {
        value
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Release>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onNext(Release value)
                    {
                        mView.hideProgressBar();
                        Log.e(TAG, "Success! " + value);
                        results.add(value);
                        recyclerViewResultsAdapter.notifyItemInserted(results.size());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e(TAG, "Error: " + e.toString());
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }
}
