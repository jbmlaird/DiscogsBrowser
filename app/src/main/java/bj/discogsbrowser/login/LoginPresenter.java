package bj.discogsbrowser.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import javax.inject.Inject;

import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.SharedPrefsManager;

/**
 * Created by Josh Laird on 15/04/2017.
 */
public class LoginPresenter implements LoginContract.Presenter
{
    private static final String TAG = "LoginPresenter";
    private LoginContract.View view;
    private SharedPrefsManager sharedPrefsManager;
    private OAuth10aService oAuth10aService;
    private AnalyticsTracker tracker;

    @Inject
    public LoginPresenter(@NonNull LoginContract.View view, @NonNull SharedPrefsManager sharedPrefsManager, @NonNull OAuth10aService oAuth10aService, @NonNull AnalyticsTracker tracker)
    {
        this.view = view;
        this.sharedPrefsManager = sharedPrefsManager;
        this.oAuth10aService = oAuth10aService;
        this.tracker = tracker;
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
