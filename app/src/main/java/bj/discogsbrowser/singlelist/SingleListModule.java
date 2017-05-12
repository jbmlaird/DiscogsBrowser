package bj.discogsbrowser.singlelist;

import bj.discogsbrowser.ActivityScope;
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
    @ActivityScope
    public SingleListContract.View provideSingleListView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    public CompositeDisposable compositeDisposable()
    {
        return new CompositeDisposable();
    }
}
