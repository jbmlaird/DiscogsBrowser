package bj.discogsbrowser.artist;

import bj.discogsbrowser.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {ArtistModule.class})
public interface ArtistComponent
{
    void inject(ArtistActivity artistActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder artistModule(ArtistModule module);

        ArtistComponent build();
    }
}
