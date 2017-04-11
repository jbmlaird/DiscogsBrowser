package bj.rxjavaexperimentation.artistreleases;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.artistreleases.fragments.ArtistReleasesFragment;
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
