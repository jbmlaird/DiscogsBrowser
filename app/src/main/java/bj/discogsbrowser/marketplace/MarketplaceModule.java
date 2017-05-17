package bj.discogsbrowser.marketplace;

import android.content.Context;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.di.scopes.ActivityScope;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.NumberFormatWrapper;
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
    @ActivityScope
    protected MarketplaceContract.View provideMarketplaceView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected MarketplaceController providesController(Context context, ImageViewAnimator imageViewAnimator, NumberFormatWrapper wrapper)
    {
        return new MarketplaceController(context, view, imageViewAnimator, wrapper);
    }

    @Provides
    @ActivityScope
    protected MarketplacePresenter providesPresenter(Context context, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider, MarketplaceController controller)
    {
        return new MarketplacePresenter(context, view, discogsInteractor, mySchedulerProvider, controller);
    }
}
