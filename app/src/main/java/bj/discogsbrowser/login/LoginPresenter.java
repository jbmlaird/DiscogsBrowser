package bj.discogsbrowser.login;

import android.support.annotation.NonNull;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.wrappers.RxSocialConnectWrapper;

/**
 * Created by Josh Laird on 15/04/2017.
 */
public class LoginPresenter implements LoginContract.Presenter
{
    private static final String TAG = "LoginPresenter";
    private LoginContract.View view;
    private SharedPrefsManager sharedPrefsManager;
    private RxSocialConnectWrapper rxSocialConnectWrapper;
    private OAuth10aService oAuth10aService;

    public LoginPresenter(@NonNull LoginContract.View view, @NonNull SharedPrefsManager sharedPrefsManager,
                          @NonNull RxSocialConnectWrapper rxSocialConnectWrapper, @NonNull OAuth10aService oAuth10aService)
    {
        this.view = view;
        this.sharedPrefsManager = sharedPrefsManager;
        this.rxSocialConnectWrapper = rxSocialConnectWrapper;
        this.oAuth10aService = oAuth10aService;
    }

    @Override
    public boolean hasUserLoggedIn()
    {
        return sharedPrefsManager.isUserLoggedIn();
    }

    @Override
    public void startOAuthService(LoginActivity loginActivity)
    {
        rxSocialConnectWrapper.with(loginActivity, oAuth10aService)
                .subscribe(response ->
                {
                    OAuth1AccessToken token = response.token();
                    sharedPrefsManager.storeOAuthToken(token);
                    view.finish();
                }, error ->
                        view.displayErrorDialog());
    }
}
