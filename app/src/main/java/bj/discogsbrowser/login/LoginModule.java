package bj.discogsbrowser.login;

import bj.discogsbrowser.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 15/04/2017.
 */
@Module
public class LoginModule
{
    private LoginContract.View view;

    public LoginModule(LoginContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    LoginContract.View provideView()
    {
        return view;
    }
}
