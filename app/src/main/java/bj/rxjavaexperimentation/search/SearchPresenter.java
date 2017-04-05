package bj.rxjavaexperimentation.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import java.util.ArrayList;

import javax.inject.Inject;

import bj.rxjavaexperimentation.model.ResultModel;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Josh Laird on 20/02/2017.
 */
public class SearchPresenter implements SearchContract.Presenter
{
    private static final String TAG = "SearchPresenter";

    private Context mContext;
    private SearchContract.View mView;
    private RecyclerViewResultsAdapter recyclerViewResultsAdapter;
    private ArrayList<ResultModel> results = new ArrayList<>();
    private Function<SearchViewQueryTextEvent, ObservableSource<?>> searchModelFunc;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public SearchPresenter(Context mContext, SearchContract.View mView, Function<SearchViewQueryTextEvent, ObservableSource<?>> searchModelFunc)
    {
        this.mContext = mContext;
        this.mView = mView;
        this.searchModelFunc = searchModelFunc;
    }

    @Override
    public void setupRecyclerView(RecyclerView rvResults)
    {
        rvResults.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewResultsAdapter = new RecyclerViewResultsAdapter(this, mContext, results);
        rvResults.setAdapter(recyclerViewResultsAdapter);
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

    @Override
    public void setupSubscription()
    {
        disposable.add(mView.searchIntent()
                .flatMap(searchModelFunc)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Object>()
                {
                    @Override
                    public void onNext(Object o)
                    {
                        Log.e(TAG, "ye");
                        // .startWith() empty string means new query
                        // Need a way to cancel existing network request upon new search query
                        if (o.equals(""))
                        {
                            mView.showProgressBar();
                            results.clear();
                            recyclerViewResultsAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            mView.hideProgressBar();
                            results.add((ResultModel) o);
                            recyclerViewResultsAdapter.notifyItemInserted(results.size() - 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e(TAG, "error");
                    }

                    @Override
                    public void onComplete()
                    {

                    }
                }));
    }
}
