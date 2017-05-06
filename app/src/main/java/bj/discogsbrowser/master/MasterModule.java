package bj.discogsbrowser.master;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Module
public class MasterModule
{
    private MasterContract.View view;

    public MasterModule(MasterContract.View view)
    {
        this.view = view;
    }

    @Provides
    @Singleton
    MasterContract.View provideMasterView()
    {
        return view;
    }
}
