package bj.discogsbrowser.release;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@ActivityScope
@Component(modules = {ReleaseModule.class}, dependencies = {AppComponent.class})
public interface ReleaseComponent
{
    void inject(ReleaseActivity releaseActivity);
}
