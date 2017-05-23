package bj.vinylbrowser.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.scribejava.core.model.OAuth1AccessToken;

import bj.vinylbrowser.R;
import bj.vinylbrowser.model.user.UserDetails;

/**
 * Created by Josh Laird on 15/04/2017.
 * <p>
 * Util class to access {@link SharedPreferences}.
 */
public class SharedPrefsManager
{
    private SharedPreferences settings;
    private Context context;

    public SharedPrefsManager(Context context)
    {
        this.context = context;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public OAuth1AccessToken getUserOAuthToken()
    {
        return new OAuth1AccessToken(settings.getString(context.getString(R.string.oauth_access_token), ""),
                settings.getString(context.getString(R.string.oauth_access_token_secret), ""));
    }

    /**
     * If the user has a token stored then they're logged in.
     *
     * @return True if there is a token.
     */
    public boolean isUserLoggedIn()
    {
        return (!getUserOAuthToken().getToken().equals(""));
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

        editor.apply();
    }

    public void storeUserDetails(UserDetails userDetails)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.avatar_url), userDetails.getAvatarUrl());
        editor.putString(context.getString(R.string.username), userDetails.getUsername());
        editor.putString(context.getString(R.string.name), userDetails.getName());
        editor.putString(context.getString(R.string.num_collection), String.valueOf(userDetails.getNumCollection()));
        editor.putString(context.getString(R.string.num_wantlist), String.valueOf(userDetails.getNumWantlist()));

        editor.apply();
    }

    public String getUsername()
    {
        return settings.getString(context.getString(R.string.username), "");
    }

    public String getAvatarUrl()
    {
        return settings.getString(context.getString(R.string.avatar_url), "");
    }

    public String getName()
    {
        return settings.getString(context.getString(R.string.name), "");
    }

    public String getNumCollection()
    {
        return settings.getString(context.getString(R.string.num_collection), "");
    }

    public String getNumWantlist()
    {
        return settings.getString(context.getString(R.string.num_wantlist), "");
    }

    /**
     * Called when the user has just modified their Collection.
     * This causes the next time their Collection is fetched to be refreshed.
     */
    public void setFetchNextCollection(String fetchNextCollection)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.fetch_next_collection), fetchNextCollection);
        editor.apply();
    }

    public boolean fetchNextCollection()
    {
        String fetchNextCollection = settings.getString(context.getString(R.string.fetch_next_collection), "");
        if (fetchNextCollection.equals("") || fetchNextCollection.equals("yes"))
        {
            setFetchNextCollection("no");
            return true;
        }
        else
            return false;
    }

    /**
     * Called when the user has just modified their Wantlist.
     * This causes the next time their Wantlist is fetched to be refreshed.
     */
    public void setFetchNextWantlist(String fetchNextWantlist)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.fetch_next_wantlist), fetchNextWantlist);
        editor.apply();
    }


    public boolean fetchNextWantlist()
    {
        String fetchNextWantlist = settings.getString(context.getString(R.string.fetch_next_wantlist), "");
        if (fetchNextWantlist.equals("") || fetchNextWantlist.equals("yes"))
        {
            setFetchNextWantlist("no");
            return true;
        }
        else
            return false;
    }

    public boolean fetchNextUserDetails()
    {
        String fetchNextUserDetails = settings.getString(context.getString(R.string.fetch_next_user_details), "");
        if (fetchNextUserDetails.equals("") || fetchNextUserDetails.equals("yes"))
        {
            setfetchNextUserDetails("no");
            return true;
        }
        else
            return false;
    }

    public void setfetchNextUserDetails(String fetchNextUserDetails)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.fetch_next_user_details), fetchNextUserDetails);
        editor.apply();
    }

    public boolean isOnBoardingCompleted()
    {
        String onboardingCompleted = settings.getString(context.getString(R.string.onboarding_completed), "");
        return onboardingCompleted.equals("onboarding_completed");
    }

    public void setOnboardingCompleted(String onboardingCompleted)
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.onboarding_completed), onboardingCompleted);
        editor.apply();
    }

    /**
     * On logout, remove all relevant user data from SharedPrefs.
     */
    void removeUserDetails()
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.oauth_access_token), "");
        editor.putString(context.getString(R.string.oauth_access_token_secret), "");
        editor.putString(context.getString(R.string.username), "");
        editor.putString(context.getString(R.string.avatar_url), "");
        editor.putString(context.getString(R.string.name), "");
        editor.putString(context.getString(R.string.num_collection), "");
        editor.putString(context.getString(R.string.num_wantlist), "");
        editor.putString(context.getString(R.string.onboarding_completed), "");

        editor.apply();
    }
}