package bj.vinylbrowser.home;

import android.content.Context;

import bj.vinylbrowser.di.scopes.FragmentScope;
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
public class HomeModule
{
    private HomeContract.View mView;

    public HomeModule(HomeContract.View view)
    {
        mView = view;
    }

    @Provides
    @FragmentScope
    protected HomeContract.View provideMainView()
    {
        return mView;
    }

    @Provides
    @FragmentScope
    protected NavigationDrawerBuilder provideNavigationDrawerBuilder(Context context, SharedPrefsManager sharedPrefsManager, DaoManager daoManager)
    {
        return new NavigationDrawerBuilder(context, sharedPrefsManager, daoManager);
    }

    @Provides
    @FragmentScope
    protected CompositeDisposable compositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @FragmentScope
    protected HomeEpxController providesMainController(Context context, SharedPrefsManager sharedPrefsManager,
                                                       ImageViewAnimator imageViewAnimator, DateFormatter dateFormatter, AnalyticsTracker tracker)
    {
        return new HomeEpxController(context, mView, sharedPrefsManager, imageViewAnimator, dateFormatter, tracker);
    }

    @Provides
    @FragmentScope
    protected HomePresenter providesMainPresenter(Context context, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                                  NavigationDrawerBuilder builder, HomeEpxController controller, SharedPrefsManager sharedPrefsManager,
                                                  LogWrapper log, DaoManager daoManager, AnalyticsTracker tracker)
    {
        return new HomePresenter(context, mView, discogsInteractor, mySchedulerProvider, builder, controller, sharedPrefsManager, log, daoManager, tracker);
    }
}
