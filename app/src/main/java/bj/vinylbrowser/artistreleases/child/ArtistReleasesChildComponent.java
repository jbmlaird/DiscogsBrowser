package bj.vinylbrowser.artistreleases.child;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@FragmentScope
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
