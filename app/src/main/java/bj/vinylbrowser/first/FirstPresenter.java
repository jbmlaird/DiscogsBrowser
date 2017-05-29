package bj.vinylbrowser.first;

import android.content.Context;
import android.support.annotation.NonNull;

import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.main.MainContract;
import bj.vinylbrowser.main.MainEpxController;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.NavigationDrawerBuilder;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.LogWrapper;

/**
 * Created by Josh Laird on 18/02/2017.
 */
public class FirstPresenter implements FirstContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private MainContract.View mView;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private NavigationDrawerBuilder navigationDrawerBuilder;
    private MainEpxController mainEpxController;
    private SharedPrefsManager sharedPrefsManager;
    private LogWrapper log;
    private DaoManager daoManager;
    private AnalyticsTracker tracker;

    public FirstPresenter(@NonNull Context context, @NonNull MainContract.View view, @NonNull DiscogsInteractor discogsInteractor,
                          @NonNull MySchedulerProvider mySchedulerProvider, @NonNull NavigationDrawerBuilder navigationDrawerBuilder,
                          @NonNull MainEpxController mainEpxController, @NonNull SharedPrefsManager sharedPrefsManager,
                          @NonNull LogWrapper log, @NonNull DaoManager daoManager, @NonNull AnalyticsTracker tracker)
    {
        this.context = context;
        mView = view;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.navigationDrawerBuilder = navigationDrawerBuilder;
        this.mainEpxController = mainEpxController;
        this.sharedPrefsManager = sharedPrefsManager;
        this.log = log;
        this.daoManager = daoManager;
        this.tracker = tracker;
    }
}
