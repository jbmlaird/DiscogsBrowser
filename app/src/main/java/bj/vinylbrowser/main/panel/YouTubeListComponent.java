package bj.vinylbrowser.main.panel;

import bj.vinylbrowser.di.scopes.ChildFragmentScope;
import bj.vinylbrowser.di.scopes.FragmentScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 01/06/2017.
 */
@ChildFragmentScope
@Subcomponent(modules = {YouTubeListModule.class})
public interface YouTubeListComponent
{
    void inject(YouTubeListFragment fragment);

    @Subcomponent.Builder
    interface Builder
    {
        Builder youTubeListModule(YouTubeListModule module);

        YouTubeListComponent build();
    }
}
