package bj.rxjavaexperimentation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.scribejava.core.model.OAuth1AccessToken;

import javax.inject.Inject;

import bj.rxjavaexperimentation.R;

/**
 * Created by Josh Laird on 15/04/2017.
 * <p>
 * Util class to access {@link SharedPreferences}.
 */
public class SharedPrefsManager
{
    private SharedPreferences settings;
    private Context context;

    @Inject
    public SharedPrefsManager(Context context)
    {
        this.context = context;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public OAuth1AccessToken getUserOAuthToken()
    {
        return new OAuth1AccessToken(settings.getString(context.getString(R.string.oauth_access_token), "")
                , settings.getString(context.getString(R.string.oauth_access_token_secret), ""));
    }

    /**
     * Temporarily store OAuth in plain text while RxSocialConnect disk cache is not working.
     *
     * @param token OAuth access token & secret.
     */
    public void storeOAuthToken(OAuth1AccessToken token)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.oauth_access_token), token.getToken());
        editor.putString(context.getString(R.string.oauth_access_token_secret), token.getTokenSecret());

        // Commit the edits!
        editor.apply();
    }

    public void removeStoredToken()
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.oauth_access_token), "");
        editor.putString(context.getString(R.string.oauth_access_token_secret), "");

        // Commit the edits!
        editor.apply();
    }
}