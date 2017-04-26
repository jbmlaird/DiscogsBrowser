package bj.rxjavaexperimentation.main;

import javax.inject.Singleton;

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
    @Singleton
    MainContract.View provideMainView()
    {
        return mView;
    }

    @Provides
    @Singleton
    CompositeDisposable compositeDisposable()
    {
        return new CompositeDisposable();
    }
}
