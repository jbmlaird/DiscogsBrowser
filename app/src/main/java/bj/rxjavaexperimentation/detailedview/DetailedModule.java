package bj.rxjavaexperimentation.detailedview;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@Module
public class DetailedModule
{
    private DetailedContract.View view;

    public DetailedModule(DetailedContract.View view)
    {
        this.view = view;
    }

    @Provides
    @Singleton
    DetailedContract.View providesDetailedView()
    {
        return view;
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable()
    {
        return new CompositeDisposable();
    }
}
