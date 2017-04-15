package bj.rxjavaexperimentation.login;

/**
 * Created by Josh Laird on 15/04/2017.
 */

public interface LoginContract
{
    interface View
    {
        void finish();
    }

    interface Presenter
    {
        boolean hasUserLoggedIn();

        void startOAuthService(LoginActivity loginActivity);
    }
}
