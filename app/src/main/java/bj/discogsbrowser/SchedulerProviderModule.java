package bj.discogsbrowser;

import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 15/05/2017.
 */
@Module
public class SchedulerProviderModule
{
    @Provides
    protected MySchedulerProvider mySchedulerProvider()
    {
        return new MySchedulerProvider();
    }
}
