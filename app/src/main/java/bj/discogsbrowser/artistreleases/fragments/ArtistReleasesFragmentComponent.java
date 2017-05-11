package bj.discogsbrowser.artistreleases.fragments;

import dagger.Subcomponent;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@Subcomponent(modules = {ArtistReleasesFragmentModule.class})
public interface ArtistReleasesFragmentComponent
{
    void inject(ArtistReleasesFragment fragment);
}
