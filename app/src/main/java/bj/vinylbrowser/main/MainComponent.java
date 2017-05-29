package bj.vinylbrowser.main;

import org.jetbrains.annotations.NotNull;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.first.FirstActivity;
import dagger.Subcomponent;

/**
 * Created by j on 18/02/2017.
 */
@ActivityScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent
{
    void inject(@NotNull MainController mainController);

    @Subcomponent.Builder
    interface Builder
    {
        Builder mainActivityModule(MainModule module);

        MainComponent build();
    }
}
