package bj.discogsbrowser.login;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 15/04/2017.
 */
@ActivityScope
@Component(modules = {LoginModule.class}, dependencies = {AppComponent.class})
public interface LoginComponent
{
    void inject(LoginActivity loginActivity);
}
