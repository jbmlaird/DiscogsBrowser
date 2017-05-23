package bj.vinylbrowser.order;

import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {OrderModule.class})
public interface OrderComponent
{
    void inject(OrderActivity orderActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder orderModule(OrderModule module);

        OrderComponent build();
    }
}
