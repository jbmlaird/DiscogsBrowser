package bj.discogsbrowser.network;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * Created by Josh Laird on 15/04/2017.
 * <p>
 * For {@link org.fuckboilerplate.rx_social_connect.RxSocialConnect} constructor.
 */

public class DiscogsOAuthApi extends DefaultApi10a
{
    private static final String AUTHORIZE_URL = "https://www.discogs.com/oauth/authorize?oauth_token=%s";
    private static final String REQUEST_TOKEN_RESOURCE = "api.discogs.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "api.discogs.com/oauth/access_token";

    protected DiscogsOAuthApi()
    {
    }

    private static class InstanceHolder
    {
        private static final DiscogsOAuthApi INSTANCE = new DiscogsOAuthApi();
    }

    public static DiscogsOAuthApi instance()
    {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getRequestTokenEndpoint()
    {
        return "https://" + REQUEST_TOKEN_RESOURCE;
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://" + ACCESS_TOKEN_RESOURCE;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken)
    {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}
