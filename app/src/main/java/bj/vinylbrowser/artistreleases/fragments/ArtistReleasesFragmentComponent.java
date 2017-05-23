package bj.vinylbrowser.artistreleases.fragments;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@FragmentScope
@Subcomponent(modules = {ArtistReleasesFragmentModule.class})
public interface ArtistReleasesFragmentComponent
{
    void inject(ArtistReleasesFragment fragment);

    @Subcomponent.Builder
    interface Builder
    {
        Builder artistReleasesFragmentModule(ArtistReleasesFragmentModule module);

        ArtistReleasesFragmentComponent build();
    }
}
