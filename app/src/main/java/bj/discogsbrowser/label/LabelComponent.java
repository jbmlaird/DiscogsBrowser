package bj.discogsbrowser.label;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@ActivityScope
@Component(modules = {LabelModule.class}, dependencies = {AppComponent.class})
public interface LabelComponent
{
    void inject(LabelActivity labelActivity);
}
