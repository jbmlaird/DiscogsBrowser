package bj.vinylbrowser.artist;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@FragmentScope
@Subcomponent(modules = {ArtistModule.class})
public interface ArtistComponent
{
    void inject(ArtistController controller);

    @Subcomponent.Builder
    interface Builder
    {
        Builder artistModule(ArtistModule module);

        ArtistComponent build();
    }
}
