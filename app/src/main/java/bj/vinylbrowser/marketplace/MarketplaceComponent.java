package bj.vinylbrowser.marketplace;

import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 13/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {MarketplaceModule.class})
public interface MarketplaceComponent
{
    void inject(MarketplaceListingActivity marketplaceListingActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder marketplaceModule(MarketplaceModule module);

        MarketplaceComponent build();
    }
}
