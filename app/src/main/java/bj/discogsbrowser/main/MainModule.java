package bj.discogsbrowser.main;

import android.content.Context;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.utils.DateFormatter;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.NavigationDrawerBuilder;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by j on 18/02/2017.
 */

@Module
public class MainModule
{
    private MainContract.View mView;

    public MainModule(MainContract.View view)
    {
        mView = view;
    }

    @Provides
    @ActivityScope
    protected MainContract.View provideMainView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    protected CompositeDisposable compositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    protected MainController providesMainController(Context context, SharedPrefsManager sharedPrefsManager,
                                                    ImageViewAnimator imageViewAnimator, DateFormatter dateFormatter, AnalyticsTracker tracker)
    {
        return new MainController(context, mView, sharedPrefsManager, imageViewAnimator, dateFormatter, tracker);
    }

    @Provides
    @ActivityScope
    protected MainPresenter providesMainPresenter(Context context, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                                  NavigationDrawerBuilder builder, MainController controller, SharedPrefsManager sharedPrefsManager,
                                                  LogWrapper log, DaoManager daoManager, AnalyticsTracker tracker)
    {
        return new MainPresenter(context, mView, discogsInteractor, mySchedulerProvider, builder, controller, sharedPrefsManager, log, daoManager, tracker);
    }
}
