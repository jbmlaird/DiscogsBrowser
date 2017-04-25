package bj.rxjavaexperimentation.master;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
@Component(dependencies = {AppComponent.class}, modules = {MasterModule.class})
public interface MasterComponent
{
    void inject(MasterActivity masterActivity);
}
