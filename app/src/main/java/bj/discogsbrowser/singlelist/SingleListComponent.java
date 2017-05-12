package bj.discogsbrowser.singlelist;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {SingleListModule.class})
public interface SingleListComponent
{
    void inject(SingleListActivity singleListActivity);
}
