package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.rxjavaexperimentation.discogs.DiscogsInteractor;
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
    private ArrayList<Object> results = new ArrayList<>();
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
        recyclerViewResultsAdapter = new RecyclerViewResultsAdapter(this, mContext, results);
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
                .subscribe(new Observer<Object>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onNext(Object value)
                    {
                        if (value == null)
                        {
                            Log.e(TAG, "Ignore");
                        }
                        else
                        {
                            mView.hideProgressBar();
                            Log.e(TAG, "Success! " + value);
                            results.add(value);
                            recyclerViewResultsAdapter.notifyItemInserted(results.size());
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.hideProgressBar();
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }

    /**
     * Shows a more detailed view of the user's selected result.
     *
     * @param ivImage Image of result.
     */
    @Override
    public void goToResult(ImageView ivImage)
    {

    }
}
