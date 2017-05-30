package bj.vinylbrowser.artistreleases;

import bj.vinylbrowser.artistreleases.child.ArtistReleasesChildComponent;
import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {ArtistReleasesModule.class})
public interface ArtistReleasesComponent
{
    void inject(ArtistReleasesController controller);

    ArtistReleasesChildComponent.Builder artistReleasesFragmentComponentBuilder();

    @Subcomponent.Builder
    interface Builder
    {
        Builder artistReleasesModule(ArtistReleasesModule artistReleasesModule);

        ArtistReleasesComponent build();
    }
}
