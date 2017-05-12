package bj.discogsbrowser.order;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@ActivityScope
@Component(modules = {OrderModule.class}, dependencies = {AppComponent.class})
public interface OrderComponent
{
    void inject(OrderActivity orderActivity);
}
