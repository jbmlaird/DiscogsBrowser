package bj.vinylbrowser.singlelist;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@FragmentScope
@Subcomponent(modules = {SingleListModule.class})
public interface SingleListComponent
{
    void inject(SingleListController controller);

    @Subcomponent.Builder
    interface Builder
    {
        Builder singleModule(SingleListModule module);

        SingleListComponent build();
    }
}
