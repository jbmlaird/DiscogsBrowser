package bj.discogsbrowser.wrappers;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.fuckboilerplate.rx_social_connect.Response;
import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import bj.discogsbrowser.login.LoginActivity;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 09/05/2017.
 * For mocking.
 */

public class RxSocialConnectWrapper
{
    public Observable<Response<LoginActivity, OAuth1AccessToken>> with(LoginActivity loginActivity, OAuth10aService oAuth10aService)
    {
        return RxSocialConnect.with(loginActivity, oAuth10aService);
    }
}
