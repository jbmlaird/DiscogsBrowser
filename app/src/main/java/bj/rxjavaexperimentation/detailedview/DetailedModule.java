package bj.rxjavaexperimentation.detailedview;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
