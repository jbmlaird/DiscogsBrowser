package bj.vinylbrowser.main

import android.content.Context
import bj.vinylbrowser.di.scopes.ActivityScope
import bj.vinylbrowser.greendao.DaoManager
import bj.vinylbrowser.utils.NavigationDrawerBuilder
import bj.vinylbrowser.utils.SharedPrefsManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Josh Laird on 29/05/2017.
 */
@Module
class MainModule(mainActivity: MainActivity) {
    lateinit var mView: MainContract.View

    fun FirstModule(view: MainContract.View) {
        mView = view
    }

    @Provides
    @ActivityScope
    protected fun provideView(): MainContract.View {
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