package bj.discogsbrowser.main;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by j on 18/02/2017.
 */
@ActivityScope
@Component(modules = {MainModule.class}, dependencies = {AppComponent.class})
public interface MainComponent
{
    void inject(MainActivity mainActivity);
}
