package bj.discogsbrowser.order;

import android.content.Context;

import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
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

    @Provides
    @ActivityScope
    protected OrderController providesController(Context context, ImageViewAnimator animator, AnalyticsTracker tracker)
    {
        return new OrderController(context, view, animator, tracker);
    }

    @Provides
    @ActivityScope
    OrderPresenter providesPresenter(DiscogsInteractor interactor, MySchedulerProvider provider, OrderController controller)
    {
        return new OrderPresenter(interactor, provider, controller);
    }
}
