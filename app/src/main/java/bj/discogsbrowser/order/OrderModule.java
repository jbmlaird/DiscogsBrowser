package bj.discogsbrowser.order;

import bj.discogsbrowser.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@Module
public class OrderModule
{
    private OrderContract.View view;

    public OrderModule(OrderContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    OrderContract.View providesView()
    {
        return view;
    }
}
