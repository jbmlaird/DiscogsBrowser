package bj.vinylbrowser.label;

import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@FragmentScope
@Subcomponent(modules = {LabelModule.class})
public interface LabelComponent
{
    void inject(LabelController labelController);

    @Subcomponent.Builder
    interface Builder
    {
        Builder labelActivityModule(LabelModule module);

        LabelComponent build();
    }
}
