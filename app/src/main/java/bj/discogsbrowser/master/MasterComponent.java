package bj.discogsbrowser.master;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {MasterModule.class})
public interface MasterComponent
{
    void inject(MasterActivity masterActivity);
}
