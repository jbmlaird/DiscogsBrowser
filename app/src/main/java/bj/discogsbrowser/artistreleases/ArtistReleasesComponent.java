package bj.discogsbrowser.artistreleases;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragment;
import dagger.Component;

/**
 * Created by Josh Laird on 10/04/2017.
 */

@Singleton
@Component(modules = {ArtistReleasesModule.class}, dependencies = {AppComponent.class})
public interface ArtistReleasesComponent
{
    void inject(ArtistReleasesActivity artistReleasesActivity);

    void inject(ArtistReleasesFragment artistReleasesFragment);
}
