package bj.vinylbrowser.order;

import android.content.Context;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    protected OrderContract.View providesView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected OrderEpxController providesController(Context context, ImageViewAnimator animator, AnalyticsTracker tracker)
    {
        return new OrderEpxController(context, view, animator, tracker);
    }

    @Provides
    @ActivityScope
    protected OrderPresenter providesPresenter(DiscogsInteractor interactor, MySchedulerProvider provider, OrderEpxController controller)
    {
        return new OrderPresenter(interactor, provider, controller);
    }
}
