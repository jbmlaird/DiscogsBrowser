package bj.rxjavaexperimentation.singlelist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.common.RecyclerViewModel;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

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

    @Inject
    public SingleListPresenter(Context context, SingleListContract.View view, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, SingleListAdapter singleListAdapter, CompositeDisposable disposable)
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
                        .subscribe(wants ->
                                {
                                    view.stopLoading();
                                    items = wants;
                                    if (items.size() == 0)
                                        view.showNoItems(true, context.getString(R.string.wantlist_none));
                                    singleListAdapter.setItems(wants);
                                    singleListAdapter.notifyDataSetChanged();
                                },
                                error ->
                                {
                                    view.stopLoading();
                                    view.showError(true, context.getString(R.string.error_wantlist));
                                }
                        );
                break;
            case "collection":
                discogsInteractor.fetchCollection(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(collectionReleases ->
                                {
                                    view.stopLoading();
                                    items = collectionReleases;
                                    if (items.size() == 0)
                                        view.showNoItems(true, context.getString(R.string.collection_none));
                                    singleListAdapter.setItems(collectionReleases);
                                    singleListAdapter.notifyDataSetChanged();
                                },
                                error ->
                                {
                                    view.stopLoading();
                                    view.showError(true, context.getString(R.string.error_collection));
                                });
                break;
            case "orders":
                discogsInteractor.fetchOrders()
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(orders ->
                                {
                                    view.stopLoading();
                                    items = orders;
                                    if (items.size() == 0)
                                        view.showNoItems(true, context.getString(R.string.orders_none));
                                    singleListAdapter.setItems(orders);
                                    singleListAdapter.notifyDataSetChanged();
                                },
                                error ->
                                {
                                    view.stopLoading();
                                    view.showError(true, context.getString(R.string.error_orders));
                                }
                        );
                break;
            case "selling":
                discogsInteractor.fetchSelling(username)
                        .observeOn(mySchedulerProvider.io())
                        .flattenAsObservable(listings -> listings)
                        .filter(listing -> listing.getStatus().equals("For Sale"))
                        .toList()
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribe(collectionReleases ->
                                {
                                    view.stopLoading();
                                    items = collectionReleases;
                                    if (items.size() == 0)
                                        view.showNoItems(true, context.getString(R.string.selling_none));
                                    else
                                        view.showNoItems(false, "");
                                    singleListAdapter.setItems(collectionReleases);
                                    singleListAdapter.notifyDataSetChanged();
                                },
                                error ->
                                {
                                    view.stopLoading();
                                    view.showError(true, context.getString(R.string.error_selling));
                                });
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
