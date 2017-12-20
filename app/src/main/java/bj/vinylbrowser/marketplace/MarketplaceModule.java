package bj.vinylbrowser.marketplace;

import android.content.Context;

import bj.vinylbrowser.di.scopes.FragmentScope;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.NumberFormatWrapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 13/04/2017.
 */
@Module
public class MarketplaceModule
{
    private MarketplaceContract.View view;

    public MarketplaceModule(MarketplaceContract.View view)
    {
        this.view = view;
    }

    @Provides
    @FragmentScope
    protected MarketplaceContract.View provideMarketplaceView()
    {
        return view;
    }

    @Provides
    @FragmentScope
    protected MarketplaceEpxController providesController(Context context, ImageViewAnimator imageViewAnimator, NumberFormatWrapper wrapper)
    {
        return new MarketplaceEpxController(context, view, imageViewAnimator, wrapper);
    }

    @Provides
    @FragmentScope
    protected MarketplacePresenter providesPresenter(DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, MarketplaceEpxController controller)
    {
        return new MarketplacePresenter(discogsInteractor, mySchedulerProvider, controller);
    }
}
