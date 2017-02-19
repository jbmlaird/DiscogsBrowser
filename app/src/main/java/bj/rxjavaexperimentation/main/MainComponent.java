package bj.rxjavaexperimentation.main;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.AppComponent;
import dagger.Component;

/**
 * Created by j on 18/02/2017.
 */

@Singleton
@Component(modules = {MainModule.class}, dependencies = {AppComponent.class})
public interface MainComponent
{
    void inject(MainActivity mainActivity);
}
