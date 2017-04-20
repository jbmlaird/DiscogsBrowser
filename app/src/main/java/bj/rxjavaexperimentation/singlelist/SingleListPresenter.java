package bj.rxjavaexperimentation.singlelist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.rxjavaexperimentation.model.common.RecyclerViewModel;
import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Josh Laird on 16/04/2017.
 */

public class SingleListPresenter implements SingleListContract.Presenter
{
    private SingleListContract.View view;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private SingleListAdapter singleListAdapter;
    private CompositeDisposable compositeDisposable;
    private List<? extends RecyclerViewModel> items = new ArrayList<>();

    @Inject
    public SingleListPresenter(SingleListContract.View view, SearchDiscogsInteractor searchDiscogsInteractor, MySchedulerProvider mySchedulerProvider, SingleListAdapter singleListAdapter, CompositeDisposable compositeDisposable)
    {
        this.view = view;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.singleListAdapter = singleListAdapter;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getData(String type, String username)
    {
        switch (type)
        {
            case "wantlist":
                searchDiscogsInteractor.fetchWantlist(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(wants ->
                                {
                                    items = wants;
                                    singleListAdapter.setItems(wants);
                                    singleListAdapter.notifyDataSetChanged();
                                    view.stopLoading();
                                },
                                error ->
                                        view.stopLoading()
                        );
                break;
            case "collection":
                searchDiscogsInteractor.fetchCollection(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(collectionReleases ->
                                {
                                    items = collectionReleases;
                                    singleListAdapter.setItems(collectionReleases);
                                    singleListAdapter.notifyDataSetChanged();
                                    view.stopLoading();
                                },
                                error ->
                                        view.stopLoading());
                break;
            case "orders":
                searchDiscogsInteractor.fetchOrders()
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(wants ->
                                {
                                    items = wants;
                                    singleListAdapter.setItems(wants);
                                    singleListAdapter.notifyDataSetChanged();
                                    view.stopLoading();
                                },
                                error ->
                                        view.stopLoading()
                        );
                break;
            case "selling":
                searchDiscogsInteractor.fetchListings(username)
                        .observeOn(mySchedulerProvider.io())
                        .flatMapIterable(listings -> listings)
                        .filter(listing -> listing.getStatus().equals("For Sale"))
                        .toList()
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(collectionReleases ->
                                {
                                    items = collectionReleases;
                                    singleListAdapter.setItems(collectionReleases);
                                    singleListAdapter.notifyDataSetChanged();
                                    view.stopLoading();
                                },
                                error ->
                                        view.stopLoading());
                break;
        }
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
        compositeDisposable.add(view.filterIntent()
                .observeOn(mySchedulerProvider.io())
                .subscribeWith(getFilterObserver()));
    }

    private DisposableObserver<CharSequence> getFilterObserver()
    {
        return new DisposableObserver<CharSequence>()
        {
            @Override
            public void onNext(CharSequence o)
            {
                Observable.fromArray(items)
                        .flatMapIterable(items -> items)
                        .filter(item ->
                                (item.getSubtitle().toLowerCase().contains(o.toString().toLowerCase())) || item.getTitle().toLowerCase().contains(o.toString().toLowerCase()))
                        .toList()
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(filteredItems ->
                        {
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
