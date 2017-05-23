package bj.vinylbrowser.release;

import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {ReleaseModule.class})
public interface ReleaseComponent
{
    void inject(ReleaseActivity releaseActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder releaseModule(ReleaseModule module);

        ReleaseComponent build();
    }
}
