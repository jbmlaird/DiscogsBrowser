package bj.vinylbrowser.master;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@FragmentScope
@Subcomponent(modules = {MasterModule.class})
public interface MasterComponent
{
    void inject(MasterController controller);

    @Subcomponent.Builder
    interface Builder
    {
        Builder masterActivityModule(MasterModule module);

        MasterComponent build();
    }
}
