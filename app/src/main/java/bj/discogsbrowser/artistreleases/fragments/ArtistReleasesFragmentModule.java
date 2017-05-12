package bj.discogsbrowser.artistreleases.fragments;

import bj.discogsbrowser.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@Module
public class ArtistReleasesFragmentModule
{
    private ArtistReleasesFragmentContract.View view;

    public ArtistReleasesFragmentModule(ArtistReleasesFragmentContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    ArtistReleasesFragmentContract.View providesView()
    {
        return view;
    }
}
