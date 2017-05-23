package bj.vinylbrowser.label;

import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {LabelModule.class})
public interface LabelComponent
{
    void inject(LabelActivity labelActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder labelActivityModule(LabelModule module);

        LabelComponent build();
    }
}
