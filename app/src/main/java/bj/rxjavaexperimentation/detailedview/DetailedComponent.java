package bj.rxjavaexperimentation.detailedview;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 07/04/2017.
 */

@Singleton
@Component(modules = {DetailedModule.class}, dependencies = {AppComponent.class})
public interface DetailedComponent
{
    void inject(DetailedActivity detailedActivity);
}
