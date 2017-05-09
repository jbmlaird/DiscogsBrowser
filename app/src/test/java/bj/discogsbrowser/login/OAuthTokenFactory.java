package bj.discogsbrowser.login;

import com.github.scribejava.core.model.OAuth1AccessToken;

/**
 * Created by Josh Laird on 09/05/2017.
 */

public class OAuthTokenFactory
{
    public OAuth1AccessToken getEmptyToken()
    {
        return new OAuth1AccessToken("", "");
    }

    public OAuth1AccessToken getValidToken()
    {
        return new OAuth1AccessToken("bj", "lairy");
    }
}
