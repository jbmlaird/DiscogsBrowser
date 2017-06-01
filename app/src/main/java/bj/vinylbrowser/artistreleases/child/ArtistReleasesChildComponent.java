package bj.vinylbrowser.artistreleases.child;

import bj.vinylbrowser.di.scopes.ChildFragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@ChildFragmentScope
@Subcomponent(modules = {ArtistReleasesChildModule.class})
public interface ArtistReleasesChildComponent
{
    void inject(ArtistReleasesChildController controller);

    @Subcomponent.Builder
    interface Builder
    {
        Builder artistReleasesFragmentModule(ArtistReleasesChildModule module);

        ArtistReleasesChildComponent build();
    }
}
