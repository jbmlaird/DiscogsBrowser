package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.rxjavaexperimentation.discogs.DiscogsInteractor;
import bj.rxjavaexperimentation.discogs.gson.release.Release;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Josh Laird on 20/02/2017.
 */
public class SearchPresenter implements SearchContract.Presenter
{
    private static final String TAG = "SearchPresenter";

    private Context mContext;
    private SearchContract.View mView;
    private RecyclerViewResultsAdapter recyclerViewResultsAdapter;
    private ArrayList<Release> results = new ArrayList<>();
    private DiscogsInteractor mInteractor;

    @Inject
    public SearchPresenter(DiscogsInteractor discogsInteractor)
    {
        mInteractor = discogsInteractor;
    }

    @Override
    public void setView(SearchContract.View view)
    {
        mView = view;
        mContext = view.getActivity();
    }

    @Override
    public void setupRecyclerView(RecyclerView rvResults)
    {
        rvResults.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewResultsAdapter = new RecyclerViewResultsAdapter(mContext, results);
        rvResults.setAdapter(recyclerViewResultsAdapter);
    }

    @Override
    public void searchDiscogs(String query)
    {
        results.clear();
        mView.showProgressBar();
        mInteractor.searchDiscogs(query)
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
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }
}
