package bj.discogsbrowser.label;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
@Component(modules = {LabelModule.class}, dependencies = {AppComponent.class})
public interface LabelComponent
{
    void inject(LabelActivity labelActivity);
}
