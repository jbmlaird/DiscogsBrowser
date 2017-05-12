package bj.discogsbrowser.artist;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@ActivityScope
@Component(modules = {ArtistModule.class}, dependencies = {AppComponent.class})
public interface ArtistComponent
{
    void inject(ArtistActivity artistActivity);
}
