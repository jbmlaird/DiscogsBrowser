package bj.discogsbrowser.marketplace;

import bj.discogsbrowser.ActivityScope;
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
    MarketplaceContract.View provideMarketplaceView()
    {
        return view;
    }
}
