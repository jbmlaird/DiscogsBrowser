package bj.discogsbrowser.artistreleases;

import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentComponent;
import bj.discogsbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {ArtistReleasesModule.class})
public interface ArtistReleasesComponent
{
    void inject(ArtistReleasesActivity artistReleasesActivity);

    ArtistReleasesFragmentComponent.Builder artistReleasesFragmentComponentBuilder();

    @Subcomponent.Builder
    interface Builder
    {
        Builder artistReleasesModule(ArtistReleasesModule artistReleasesModule);

        ArtistReleasesComponent build();
    }
}
