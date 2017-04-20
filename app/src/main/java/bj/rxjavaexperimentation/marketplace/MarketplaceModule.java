package bj.rxjavaexperimentation.marketplace;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

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
    @Singleton
    MarketplaceContract.View provideMarketplaceView()
    {
        return view;
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable()
    {
        return new CompositeDisposable();
    }
}
