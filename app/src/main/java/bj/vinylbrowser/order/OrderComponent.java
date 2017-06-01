package bj.vinylbrowser.order;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@FragmentScope
@Subcomponent(modules = {OrderModule.class})
public interface OrderComponent
{
    void inject(OrderController controller);

    @Subcomponent.Builder
    interface Builder
    {
        Builder orderModule(OrderModule module);

        OrderComponent build();
    }
}
