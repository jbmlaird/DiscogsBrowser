package bj.vinylbrowser.main;

import android.content.Context;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.first.FirstPresenter;
import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.DateFormatter;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.NavigationDrawerBuilder;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.LogWrapper;
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
    protected NavigationDrawerBuilder provideNavigationDrawerBuilder(Context context, SharedPrefsManager sharedPrefsManager, DaoManager daoManager)
    {
        return new NavigationDrawerBuilder(context, sharedPrefsManager, daoManager);
    }

    @Provides
    @ActivityScope
    protected CompositeDisposable compositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    protected MainEpxController providesMainController(Context context, SharedPrefsManager sharedPrefsManager,
                                                       ImageViewAnimator imageViewAnimator, DateFormatter dateFormatter, AnalyticsTracker tracker)
    {
        return new MainEpxController(context, mView, sharedPrefsManager, imageViewAnimator, dateFormatter, tracker);
    }

    @Provides
    @ActivityScope
    protected FirstPresenter providesMainPresenter(Context context, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                                   NavigationDrawerBuilder builder, MainEpxController controller, SharedPrefsManager sharedPrefsManager,
                                                   LogWrapper log, DaoManager daoManager, AnalyticsTracker tracker)
    {
        return new FirstPresenter(context, mView, discogsInteractor, mySchedulerProvider, builder, controller, sharedPrefsManager, log, daoManager, tracker);
    }
}
