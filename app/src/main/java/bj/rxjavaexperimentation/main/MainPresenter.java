package bj.rxjavaexperimentation.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.NavigationDrawerBuilder;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.Single;

/**
 * Created by j on 18/02/2017.
 */
@Singleton
public class MainPresenter implements MainContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private MainContract.View mView;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private NavigationDrawerBuilder navigationDrawerBuilder;
    private MainController mainController;
    private SharedPrefsManager sharedPrefsManager;
    private LogWrapper log;

    @Inject
    public MainPresenter(@NonNull MainContract.View view, @NonNull DiscogsInteractor discogsInteractor,
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
    public void connectAndBuildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar)
    {
        mView.showLoading(true);
        fetchMainPageInformation()
                .subscribe(listing ->
                        {
                            mainController.setSelling(listing);
                            // As RecyclerView gets detached, these must be called after attaching NavDrawer
                            mView.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar));
                            mView.setupRecyclerView();
                        },
                        error ->
                        {
                            if (error.getCause().getCause().getMessage().equals("HTTP 403 FORBIDDEN"))
                                mainController.setConfirmEmail(true);
                            else
                                mainController.setOrdersError(true);
                            mView.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar));
                            mView.setupRecyclerView();
                            error.printStackTrace();
                            log.e(TAG, "Wtf");
                        });
    }

    private Single<List<Listing>> fetchMainPageInformation()
    {
        return discogsInteractor.fetchUserDetails()
                .observeOn(mySchedulerProvider.ui())
                .flatMap(userDetails ->
                {
                    sharedPrefsManager.storeUserDetails(userDetails);
                    return fetchOrders();
                })
                .flatMap(orders ->
                {
                    mainController.setOrders(orders);
                    return fetchSelling();
                })
                .flattenAsObservable(listings -> listings)
                .filter(listing -> listing.getStatus().equals("For Sale"))
                .toList();
    }

    @Override
    public void retry()
    {
        fetchMainPageInformation()
                .subscribe(listing ->
                                mainController.setSelling(listing),
                        error ->
                        {
                            error.printStackTrace();
                            mainController.setOrdersError(true);
                        });
    }

    @Override
    public Single<List<Order>> fetchOrders()
    {
        return discogsInteractor.fetchOrders()
                .observeOn(mySchedulerProvider.ui())
                .subscribeOn(mySchedulerProvider.io())
                .doOnSubscribe(disposable -> mainController.setLoadingMorePurchases(true));
    }

    @Override
    public Single<List<Listing>> fetchSelling()
    {
        return discogsInteractor.fetchSelling(sharedPrefsManager.getUsername())
                .observeOn(mySchedulerProvider.ui())
                .subscribeOn(mySchedulerProvider.io());
    }

    @Override
    public void setupRecyclerView(MainActivity mainActivity, RecyclerView recyclerView)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        recyclerView.setAdapter(mainController.getAdapter());
        mainController.requestModelBuild();
    }
}
