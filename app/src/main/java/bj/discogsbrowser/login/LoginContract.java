package bj.discogsbrowser.login;

/**
 * Created by Josh Laird on 15/04/2017.
 */

public interface LoginContract
{
    interface View
    {
        void finish();

        void displayErrorDialog();
    }

    interface Presenter
    {
        boolean hasUserLoggedIn();

        void startOAuthService(LoginActivity loginActivity);
    }
}
