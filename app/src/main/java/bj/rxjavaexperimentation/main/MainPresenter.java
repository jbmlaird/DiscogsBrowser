package bj.rxjavaexperimentation.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.main.epoxy.MainController;
import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.utils.NavigationDrawerBuilder;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by j on 18/02/2017.
 */
@Singleton
public class MainPresenter implements MainContract.Presenter
{
    private static final String TAG = "MainPresenter";
    private MainContract.View mView;
    private SearchDiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private NavigationDrawerBuilder navigationDrawerBuilder;
    private MainController mainController;
    private SharedPrefsManager sharedPrefsManager;
    private LogWrapper log;
    private UserDetails userDetails;
    private RecyclerView recyclerView;
    private PublishSubject<List<Order>> orderPublishSubject = PublishSubject.create();
    private PublishSubject<List<Listing>> sellingPublishSubject = PublishSubject.create();

    @Inject
    public MainPresenter(@NonNull MainContract.View view, @NonNull SearchDiscogsInteractor discogsInteractor,
                         @NonNull MySchedulerProvider mySchedulerProvider, @NonNull NavigationDrawerBuilder navigationDrawerBuilder,
                         @NonNull MainController mainController, @NonNull SharedPrefsManager sharedPrefsManager, @NonNull LogWrapper log)
    {
        mView = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.navigationDrawerBuilder = navigationDrawerBuilder;
        this.mainController = mainController;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
    }

    @Override
    public void buildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar)
    {
        discogsInteractor.fetchUserDetails()
                .observeOn(mySchedulerProvider.ui())
                .subscribe(userDetails ->
                        {
                            this.userDetails = userDetails;
                            sharedPrefsManager.storeUserDetails(userDetails);
                            mView.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar, userDetails));
                            mView.setupRecyclerView();
//                    toolbar.setTitle(userDetails.getUsername());
                            mView.stopLoading();
                            fetchOrders();
                            fetchSelling();
                        },
                        error ->
                                // TODO: Implement proper error handling here
                                log.e(TAG, "Wtf"));
    }

    private void fetchOrders()
    {
        discogsInteractor.fetchOrders()
                .observeOn(mySchedulerProvider.ui())
                .doOnSubscribe(disposable -> mainController.setLoadingMorePurchases(true))
                .subscribeOn(mySchedulerProvider.io())
                .subscribe(orderPublishSubject);
    }

    private void fetchSelling()
    {
        discogsInteractor.fetchListings(userDetails.getUsername())
                .observeOn(mySchedulerProvider.ui())
                .doOnSubscribe(disposable -> mainController.setLoadingMoreSales(true))
                .subscribeOn(mySchedulerProvider.io())
                .subscribe(sellingPublishSubject);
    }

    @Override
    public void setupRecyclerView(MainActivity mainActivity, RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        recyclerView.setAdapter(mainController.getAdapter());
    }

    @Override
    public void setupObservers()
    {
        sellingPublishSubject
                .flatMapIterable(listings -> listings)
                .filter(listing -> listing.getStatus().equals("For Sale"))
                .toList()
                .observeOn(mySchedulerProvider.ui())
                .subscribe(listings ->
                                mainController.setSelling(listings),
                        error ->
                                Log.e(TAG, error.getMessage()));

        orderPublishSubject
                .observeOn(mySchedulerProvider.ui())
                .subscribe(orders ->
                                mainController.setPurchases(orders),
                        error ->
                                Log.e(TAG, error.getMessage()));
    }

    @Override
    public UserDetails getUserDetails()
    {
        return userDetails;
    }
}
