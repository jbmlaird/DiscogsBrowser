package bj.rxjavaexperimentation.login;

import javax.inject.Singleton;

import bj.rxjavaexperimentation.AppComponent;
import dagger.Component;

/**
 * Created by Josh Laird on 15/04/2017.
 */
@Singleton
@Component(modules = {LoginModule.class}, dependencies = {AppComponent.class})
public interface LoginComponent
{
    void inject(LoginActivity loginActivity);
}
