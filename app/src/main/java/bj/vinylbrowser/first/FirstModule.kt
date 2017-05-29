package bj.vinylbrowser.first

import android.content.Context
import bj.vinylbrowser.di.scopes.ActivityScope
import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.main.MainPresenter
import bj.vinylbrowser.main.MainEpxController
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.NavigationDrawerBuilder
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider
import bj.vinylbrowser.wrappers.LogWrapper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Josh Laird on 29/05/2017.
 */
@Module
class FirstModule(firstActivity: FirstActivity) {
    lateinit var mView: FirstContract.View

    fun FirstModule(view: FirstContract.View) {
        mView = view
    }

    @Provides
    @ActivityScope
    protected fun provideView(): FirstContract.View {
        return mView
    }

    @Provides
    @ActivityScope
    protected fun provideNavigationDrawerBuilder(context: Context, sharedPrefsManager: SharedPrefsManager, daoManager: DaoManager): NavigationDrawerBuilder {
        return NavigationDrawerBuilder(context, sharedPrefsManager, daoManager)
    }

    @Provides
    @ActivityScope
    protected fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

//    @Provides
//    @ActivityScope
//    protected fun providesFirstPresenter(context: Context, discogsInteractor: DiscogsInteractor, mySchedulerProvider: MySchedulerProvider,
//                                        builder: NavigationDrawerBuilder, controller: MainEpxController, sharedPrefsManager: SharedPrefsManager,
//                                        log: LogWrapper, daoManager: DaoManager, tracker: AnalyticsTracker): MainPresenter {
//        return MainPresenter(context, mView, discogsInteractor, mySchedulerProvider, builder, controller, sharedPrefsManager, log, daoManager, tracker)
//    }
}