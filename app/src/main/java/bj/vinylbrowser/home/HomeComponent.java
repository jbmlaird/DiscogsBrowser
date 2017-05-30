package bj.vinylbrowser.home;

import org.jetbrains.annotations.NotNull;

import bj.vinylbrowser.di.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by j on 18/02/2017.
 */
@ActivityScope
@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent
{
    void inject(@NotNull HomeController homeController);

    @Subcomponent.Builder
    interface Builder
    {
        Builder mainActivityModule(HomeModule module);

        HomeComponent build();
    }
}
