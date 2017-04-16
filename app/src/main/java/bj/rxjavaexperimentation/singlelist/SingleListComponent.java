package bj.rxjavaexperimentation.singlelist;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@Singleton
@Component(dependencies = {AppComponent.class}, modules = {SingleListModule.class})
public interface SingleListComponent
{
    void inject(SingleListActivity singleListActivity);
}
