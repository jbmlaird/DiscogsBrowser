package bj.vinylbrowser.marketplace;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 13/04/2017.
 */
@FragmentScope
@Subcomponent(modules = {MarketplaceModule.class})
public interface MarketplaceComponent
{
    void inject(MarketplaceController controller);

    @Subcomponent.Builder
    interface Builder
    {
        Builder marketplaceModule(MarketplaceModule module);

        MarketplaceComponent build();
    }
}
