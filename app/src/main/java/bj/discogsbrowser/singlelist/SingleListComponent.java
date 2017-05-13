package bj.discogsbrowser.singlelist;

import bj.discogsbrowser.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {SingleListModule.class})
public interface SingleListComponent
{
    void inject(SingleListActivity singleListActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder singleModule(SingleListModule module);

        SingleListComponent build();
    }
}
