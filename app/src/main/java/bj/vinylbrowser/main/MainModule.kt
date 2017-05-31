package bj.vinylbrowser.main

import bj.vinylbrowser.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by Josh Laird on 29/05/2017.
 *
 * Method and class must be declared open for DaggerMock
 */
@Module
open class MainModule(mainActivity: MainActivity) {
    lateinit var mView: MainContract.View

    fun FirstModule(view: MainContract.View) {
        mView = view
    }

    @Provides
    @ActivityScope
    open protected fun provideView(): MainContract.View {
        return mView
    }

//    @Provides
//    @ActivityScope
//    open protected fun provideNavigationDrawerBuilder(context: Context, sharedPrefsManager: SharedPrefsManager, daoManager: DaoManager): NavigationDrawerBuilder {
//        return NavigationDrawerBuilder(context, sharedPrefsManager, daoManager)
//    }
//
//    @Provides
//    @ActivityScope
//    open protected fun compositeDisposable(): CompositeDisposable {
//        return CompositeDisposable()
//    }

//    @Provides
//    @ActivityScope
//    protected fun providesFirstPresenter(context: Context, discogsInteractor: DiscogsInteractor, mySchedulerProvider: MySchedulerProvider,
//                                        builder: NavigationDrawerBuilder, controller: MainEpxController, sharedPrefsManager: SharedPrefsManager,
//                                        log: LogWrapper, daoManager: DaoManager, tracker: AnalyticsTracker): MainPresenter {
//        return MainPresenter(context, mView, discogsInteractor, mySchedulerProvider, builder, controller, sharedPrefsManager, log, daoManager, tracker)
//    }
}