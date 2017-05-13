package bj.discogsbrowser.login;

import bj.discogsbrowser.scopes.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Josh Laird on 15/04/2017.
 */
@ActivityScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent
{
    void inject(LoginActivity loginActivity);

    @Subcomponent.Builder
    interface Builder
    {
        Builder loginActivityModule(LoginModule module);

        LoginComponent build();
    }
}
