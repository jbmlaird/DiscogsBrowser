package bj.discogsbrowser.artist;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 07/04/2017.
 */

@Singleton
@Component(modules = {ArtistModule.class}, dependencies = {AppComponent.class})
public interface ArtistComponent
{
    void inject(ArtistActivity artistActivity);
}
