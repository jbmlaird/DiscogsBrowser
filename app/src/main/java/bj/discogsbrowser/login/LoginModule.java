package bj.discogsbrowser.login;

import javax.inject.Singleton;

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
    @Singleton
    LoginContract.View provideView()
    {
        return view;
    }
}
