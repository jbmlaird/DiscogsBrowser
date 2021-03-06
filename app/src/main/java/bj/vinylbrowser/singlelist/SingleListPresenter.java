package bj.vinylbrowser.singlelist;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import bj.vinylbrowser.R;
import bj.vinylbrowser.model.common.RecyclerViewModel;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.FilterHelper;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    private MySchedulerProvider mySchedulerProvider;
    private SingleListEpxController controller;
    private CompositeDisposable disposable;
    private FilterHelper filterHelper;
    private List<? extends RecyclerViewModel> items = new ArrayList<>();

    public SingleListPresenter(Context context, SingleListContract.View view, DiscogsInteractor discogsInteractor,
                               MySchedulerProvider mySchedulerProvider, SingleListEpxController controller, CompositeDisposable disposable,
                               FilterHelper filterHelper)
    {
        this.context = context;
        this.view = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.controller = controller;
        this.disposable = disposable;
        this.filterHelper = filterHelper;
    }

    /**
     * Sets up an Observer on the SearchView.
     */
    @Override
    public void setupFilterSubscription()
    {
        disposable.add(view.filterIntent()
                .observeOn(mySchedulerProvider.io())
                .subscribeWith(getFilterObserver()));
    }

    /**
     * Fetches relevant information from Discogs depending on the data type.
     *
     * @param stringId Orders, Selling, Wantlist or Collection
     * @param username User's username.
     */
    @Override
    public void getData(Integer stringId, String username)
    {
        switch (stringId)
        {
            case R.string.drawer_item_wantlist:
                discogsInteractor.fetchWantlist(username)
                        .subscribeOn(mySchedulerProvider.io())
                        .observeOn(mySchedulerProvider.ui())
                        .subscribeWith(getNetworkObserver(context.getString(R.string.error_wantlist)));
                break;
            case R.string.drawer_item_collection:
                discogsInteractor.fetchCollection(username)
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

    /**
     * Observer that subscribes to the network response.
     *
     * @param errorMessage Error message relevant to data type.
     * @return Observer.
     */
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

    /**
     * Observer that subscribes to the filter EditText.
     */
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

    /**
     * Displays items depending on the filter query.
     */
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
