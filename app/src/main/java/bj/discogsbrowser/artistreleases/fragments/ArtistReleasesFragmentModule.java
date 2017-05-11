package bj.discogsbrowser.artistreleases.fragments;

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
    ArtistReleasesFragmentContract.View providesView()
    {
        return view;
    }
}
