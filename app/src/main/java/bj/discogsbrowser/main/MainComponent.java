package bj.discogsbrowser.main;

import bj.discogsbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by j on 18/02/2017.
 */
@ActivityScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent
{
    void inject(MainActivity mainActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder mainActivityModule(MainModule module);

        MainComponent build();
    }
}
