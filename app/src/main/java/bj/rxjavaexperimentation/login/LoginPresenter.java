package bj.rxjavaexperimentation.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import javax.inject.Inject;

import bj.rxjavaexperimentation.utils.SharedPrefsManager;

/**
 * Created by Josh Laird on 15/04/2017.
 */
public class LoginPresenter implements LoginContract.Presenter
{
    private static final String TAG = "LoginPresenter";
    private LoginContract.View view;
    private SharedPrefsManager sharedPrefsManager;
    private OAuth10aService oAuth10aService;

    @Inject
    public LoginPresenter(@NonNull LoginContract.View view, @NonNull SharedPrefsManager sharedPrefsManager, @NonNull OAuth10aService oAuth10aService)
    {
        this.view = view;
        this.sharedPrefsManager = sharedPrefsManager;
        this.oAuth10aService = oAuth10aService;
    }

    @Override
    public boolean hasUserLoggedIn()
    {
        OAuth1AccessToken userOAuthToken = sharedPrefsManager.getUserOAuthToken();
        return !userOAuthToken.getToken().equals("");
    }

    @Override
    public void startOAuthService(LoginActivity loginActivity)
    {
        // TODO: If the user logs out and doesn't close the app, this response will return the details from the first call instantly
        RxSocialConnect.with(loginActivity, oAuth10aService)
                .subscribe(response ->
                {
                    OAuth1AccessToken token = response.token();
                    sharedPrefsManager.storeOAuthToken(token);
                    Log.e(TAG, token.getToken());
                    view.finish();
                }, error ->
                        Log.e(TAG, error.getMessage()));
    }
}
