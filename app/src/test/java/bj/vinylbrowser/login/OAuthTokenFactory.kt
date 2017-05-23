package bj.vinylbrowser.login

import com.github.scribejava.core.model.OAuth1AccessToken

/**
 * Created by Josh Laird on 21/05/2017.
 */
object OAuthTokenFactory {
    @JvmStatic fun getEmptyToken(): OAuth1AccessToken {
        return OAuth1AccessToken("", "")
    }

    @JvmStatic fun getValidToken(): OAuth1AccessToken {
        return OAuth1AccessToken("bj", "lairy")
    }
}