package bj.discogsbrowser.artistreleases;

import android.content.Context;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentComponent;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentModule;
import dagger.Component;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@ActivityScope
@Component(modules = {ArtistReleasesModule.class}, dependencies = {AppComponent.class})
public interface ArtistReleasesComponent
{
    void inject(ArtistReleasesActivity artistReleasesActivity);

    ArtistReleasesFragmentComponent plus(ArtistReleasesFragmentModule module);

    Context getContext();
}
