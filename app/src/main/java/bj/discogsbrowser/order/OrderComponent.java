package bj.discogsbrowser.order;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@Component(modules = {OrderModule.class}, dependencies = {AppComponent.class})
@Singleton
public interface OrderComponent
{
    void inject(OrderActivity orderActivity);
}
