package bj.discogsbrowser.artist;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@Module
public class ArtistModule
{
    private ArtistContract.View view;

    public ArtistModule(ArtistContract.View view)
    {
        this.view = view;
    }

    @Provides
    @Singleton
    ArtistContract.View providesDetailedView()
    {
        return view;
    }
}
