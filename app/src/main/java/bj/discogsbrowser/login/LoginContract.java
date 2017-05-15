package bj.discogsbrowser.login;

/**
 * Created by Josh Laird on 15/04/2017.
 */

public interface LoginContract
{
    interface View
    {
        void finishActivityLaunchMain();

        void displayErrorDialog();
    }

    interface Presenter
    {
        void startOAuthService(LoginActivity loginActivity);
    }
}
