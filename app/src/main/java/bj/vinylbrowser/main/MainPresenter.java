package bj.vinylbrowser.main;

import android.content.Context;
import android.support.annotation.NonNull;

import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.home.HomeContract;
import bj.vinylbrowser.home.HomeEpxController;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.NavigationDrawerBuilder;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.LogWrapper;

/**
 * Created by Josh Laird on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private HomeContract.View mView;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private NavigationDrawerBuilder navigationDrawerBuilder;
    private HomeEpxController homeEpxController;
    private SharedPrefsManager sharedPrefsManager;
    private LogWrapper log;
    private DaoManager daoManager;
    private AnalyticsTracker tracker;

    public MainPresenter(@NonNull Context context, @NonNull HomeContract.View view, @NonNull DiscogsInteractor discogsInteractor,
                         @NonNull MySchedulerProvider mySchedulerProvider, @NonNull NavigationDrawerBuilder navigationDrawerBuilder,
                         @NonNull HomeEpxController homeEpxController, @NonNull SharedPrefsManager sharedPrefsManager,
                         @NonNull LogWrapper log, @NonNull DaoManager daoManager, @NonNull AnalyticsTracker tracker)
    {
        this.context = context;
        mView = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.navigationDrawerBuilder = navigationDrawerBuilder;
        this.homeEpxController = homeEpxController;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
        this.daoManager = daoManager;
        this.tracker = tracker;
    }
}
