package bj.rxjavaexperimentation.singlelist;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@Module
public class SingleListModule
{
    private SingleListContract.View view;

    public SingleListModule(SingleListContract.View view)
    {
        this.view = view;
    }

    @Provides
    @Singleton
    public SingleListContract.View provideSingleListView()
    {
        return view;
    }

    @Provides
    @Singleton
    public CompositeDisposable compositeDisposable()
    {
        return new CompositeDisposable();
    }
}
