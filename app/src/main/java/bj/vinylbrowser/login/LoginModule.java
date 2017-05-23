package bj.vinylbrowser.login;

import com.github.scribejava.core.oauth.OAuth10aService;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.wrappers.RxSocialConnectWrapper;
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
    protected LoginContract.View provideView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected LoginPresenter providePresenter(SharedPrefsManager sharedPrefsManager, RxSocialConnectWrapper wrapper, OAuth10aService service)
    {
        return new LoginPresenter(view, sharedPrefsManager, wrapper, service);
    }
}
