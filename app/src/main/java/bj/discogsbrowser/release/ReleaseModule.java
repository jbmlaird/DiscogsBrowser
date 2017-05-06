package bj.discogsbrowser.release;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Module
public class ReleaseModule
{
    private ReleaseContract.View mView;

    public ReleaseModule(ReleaseContract.View view)
    {
        mView = view;
    }

    @Provides
    @Singleton
    ReleaseContract.View provideReleaseView()
    {
        return mView;
    }
}
