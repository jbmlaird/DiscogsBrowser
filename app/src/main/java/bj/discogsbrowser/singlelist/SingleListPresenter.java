package bj.discogsbrowser.singlelist;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.R;
import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.FilterHelper;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Josh Laird on 16/04/2017.
 * <p>
 * Decouple this and have a separate activity for each type?
 */
public class SingleListPresenter implements SingleListContract.Presenter
{
    private Context context;
    private SingleListContract.View view;
    private DiscogsInteractor discogsInteractor;
    private CollectionWantlistInteractor collectionWantlistInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private SingleListController controller;
    private CompositeDisposable disposable;
    private FilterHelper filterHelper;
    private List<? extends RecyclerViewModel> items = new ArrayList<>();

    public SingleListPresenter(Context context, SingleListContract.View view, DiscogsInteractor discogsInteractor, CollectionWantlistInteractor collectionWantlistInteractor,
                               MySchedulerProvider mySchedulerProvider, SingleListController controller, CompositeDisposable disposable,
                               FilterHelper filterHelper)
    {
        this.context = context;
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.collectionWantlistInteractor = collectionWantlistInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.controller = controller;
        this.disposable = disposable;
        this.filterHelper = filterHelper;
    }

    @Override
    public void setupFilterSubscription()
    {
        disposable.add(view.filterIntent()
                .observeOn(mySchedulerProvider.io())
                .subscribeWith(getFilterObserver()));
    }

    @Override
    public void getData(Integer stringId, String username)
    {
        switch (stringId)
        {
            case R.string.drawer_item_wantlist:
                collectionWantlistInteractor.fetchWantlist(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.error_wantlist)));
                break;
            case R.string.drawer_item_collection:
                collectionWantlistInteractor.fetchCollection(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.error_collection)));
                break;
            case R.string.orders:
                discogsInteractor.fetchOrders()
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.error_orders)));
                break;
            case R.string.selling:
                discogsInteractor.fetchSelling(username)
                        .observeOn(mySchedulerProvider.io())
                        .compose(filterHelper.filterForSale())
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.error_selling)));
                break;
        }
    }

    private DisposableSingleObserver<List<? extends RecyclerViewModel>> getNetworkObserver(String errorMessage)
    {
        return new DisposableSingleObserver<List<? extends RecyclerViewModel>>()
        {
            @Override
            public void onSuccess(@NonNull List<? extends RecyclerViewModel> recyclerViewModels)
            {
                items = recyclerViewModels;
                displayItems();
            }

            @Override
            public void onError(Throwable e)
            {
                controller.setError(errorMessage);
            }
        };
    }

    @Override
    public void unsubscribe()
    {
        disposable.clear();
    }

    @Override
    public void dispose()
    {
        disposable.dispose();
    }

    private DisposableObserver<CharSequence> getFilterObserver()
    {
        return new DisposableObserver<CharSequence>()
        {
            @Override
            public void onNext(CharSequence o)
            {
                filterHelper.setFilterText(o.toString().toLowerCase());
                if (items.size() > 0)
                    displayItems();
            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onComplete()
            {

            }
        };
    }

    private void displayItems()
    {
        Single.just(items)
                .subscribeOn(mySchedulerProvider.io())
                .compose(filterHelper.filterByFilterText())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(filteredItems ->
                        controller.setItems(filteredItems));
    }


    @VisibleForTesting(otherwise = 5)
    public void setItems(List<RecyclerViewModel> recyclerViewModels)
    {
        this.items = recyclerViewModels;
    }
}
