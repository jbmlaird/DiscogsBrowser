package bj.vinylbrowser.singlelist;

import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@ActivityScope
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
