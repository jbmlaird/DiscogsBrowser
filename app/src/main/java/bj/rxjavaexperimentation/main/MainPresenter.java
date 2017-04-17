package bj.rxjavaexperimentation.main;

import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.utils.NavigationDrawerBuilder;

/**
 * Created by j on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private static final String TAG = "MainPresenter";
    private MainContract.View mView;
    private SearchDiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private NavigationDrawerBuilder navigationDrawerBuilder;

    @Inject
    public MainPresenter(MainContract.View view, SearchDiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, NavigationDrawerBuilder navigationDrawerBuilder)
    {
        mView = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.navigationDrawerBuilder = navigationDrawerBuilder;
    }

    @Override
    public void buildNavigationDrawer(MainActivity mainActivity, Toolbar toolbar)
    {
        discogsInteractor.fetchUserDetails()
                .observeOn(mySchedulerProvider.ui())
                .subscribe(userDetails ->
                {
                    mView.setDrawer(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar, userDetails));
                    toolbar.setTitle(userDetails.getUsername());
                });
    }
}
