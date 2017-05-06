package bj.discogsbrowser.release;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
@Component(modules = {ReleaseModule.class}, dependencies = {AppComponent.class})
public interface ReleaseComponent
{
    void inject(ReleaseActivity releaseActivity);
}
