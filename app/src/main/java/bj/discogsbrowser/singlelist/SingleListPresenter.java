package bj.discogsbrowser.singlelist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.R;
import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@Singleton
public class SingleListPresenter implements SingleListContract.Presenter
{
    private Context context;
    private SingleListContract.View view;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private SingleListAdapter singleListAdapter;
    private CompositeDisposable disposable;
    private List<? extends RecyclerViewModel> items = new ArrayList<>();
    private String filterText = "";

    @Inject
    public SingleListPresenter(Context context, SingleListContract.View view, DiscogsInteractor discogsInteractor,
                               MySchedulerProvider mySchedulerProvider, SingleListAdapter singleListAdapter, CompositeDisposable disposable)
    {
        this.context = context;
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.singleListAdapter = singleListAdapter;
        this.disposable = disposable;
    }

    @Override
    public void getData(String type, String username)
    {
        switch (type)
        {
            case "wantlist":
                discogsInteractor.fetchWantlist(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .flattenAsObservable(wants -> wants)
                        .filter(wants ->
                                wants.getSubtitle().toLowerCase().contains(filterText) || wants.getTitle().toLowerCase().contains(filterText))
                        .toList()
                        .subscribeWith(getNetworkObserver(context.getString(R.string.wantlist_none), context.getString(R.string.error_wantlist)));
                break;
            case "collection":
                discogsInteractor.fetchCollection(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .flattenAsObservable(collection -> collection)
                        .filter(collection ->
                                collection.getSubtitle().toLowerCase().contains(filterText) || collection.getTitle().toLowerCase().contains(filterText))
                        .toList()
                        .subscribeWith(getNetworkObserver(context.getString(R.string.collection_none), context.getString(R.string.error_collection)));
                break;
            case "orders":
                discogsInteractor.fetchOrders()
                        .subscribeOn(mySchedulerProvider.io())
                        .flattenAsObservable(listings -> listings)
                        .filter(listing -> listing.getStatus().equals("For Sale"))
                        .filter(order ->
                                order.getSubtitle().toLowerCase().contains(filterText) || order.getTitle().toLowerCase().contains(filterText))
                        .toList()
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.orders_none), context.getString(R.string.error_orders)));
                break;
            case "selling":
                discogsInteractor.fetchSelling(username)
                        .observeOn(mySchedulerProvider.io())
                        .flattenAsObservable(listings -> listings)
                        .filter(listing ->
                                listing.getSubtitle().toLowerCase().contains(filterText) || listing.getTitle().toLowerCase().contains(filterText))
                        .toList()
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.not_selling_anything), context.getString(R.string.error_selling)));
                break;
        }
    }

    private DisposableSingleObserver<List<? extends RecyclerViewModel>> getNetworkObserver(String noItemsMessage, String errorMessage)
    {
        return new DisposableSingleObserver<List<? extends RecyclerViewModel>>()
        {
            @Override
            public void onSuccess(@NonNull List<? extends RecyclerViewModel> recyclerViewModels)
            {
                view.stopLoading();
                items = recyclerViewModels;
                if (items.size() == 0)
                    view.showNoItems(true, noItemsMessage);
                else
                    view.showNoItems(false, "");
                singleListAdapter.setItems(recyclerViewModels);
                singleListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e)
            {
                view.stopLoading();
                view.showError(true, errorMessage);
            }
        };
    }

    @Override
    public void setupRecyclerView(SingleListActivity singleListActivity, RecyclerView recyclerView)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(singleListActivity));
        recyclerView.setAdapter(singleListAdapter);
    }

    @Override
    public void setupFilterSubscription()
    {
        disposable.add(view.filterIntent()
                .observeOn(mySchedulerProvider.io())
                .subscribeWith(getFilterObserver()));
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
                filterText = o.toString().toLowerCase();
                if (items.size() > 0)
                    Single.just(items)
                            .flattenAsObservable(items -> items)
                            .filter(item ->
                                    (item.getSubtitle().toLowerCase().contains(o.toString().toLowerCase())) || item.getTitle().toLowerCase().contains(o.toString().toLowerCase()))
                            .toList()
                            .observeOn(mySchedulerProvider.ui())
                            .subscribe(filteredItems ->
                            {
                                if (filteredItems.size() == 0 && o.length() > 0)
                                    view.showNoItems(true, "No items");
                                else
                                    view.showNoItems(false, "");
                                singleListAdapter.setItems(filteredItems);
                                singleListAdapter.notifyDataSetChanged();
                            });
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
}
