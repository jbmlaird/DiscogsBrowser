package bj.discogsbrowser.login;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.fuckboilerplate.rx_social_connect.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.wrappers.RxSocialConnectWrapper;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest
{
    private LoginPresenter presenter;
    @Mock LoginContract.View view;
    @Mock SharedPrefsManager sharedPrefsManager;
    @Mock RxSocialConnectWrapper rxSocialConnectWrapper;
    @Mock OAuth10aService oAuth10aService;
    private OAuthTokenFactory oAuthTokenFactory = new OAuthTokenFactory();

    @Before
    public void setUp()
    {
        presenter = new LoginPresenter(view, sharedPrefsManager, rxSocialConnectWrapper, oAuth10aService);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, sharedPrefsManager, oAuth10aService);
    }

    @Test
    public void startOAuthServiceValid_logsIn()
    {
        LoginActivity mockLoginActivity = mock(LoginActivity.class);
        when(view.getActivity()).thenReturn(mockLoginActivity);
        when(rxSocialConnectWrapper.with(mockLoginActivity, oAuth10aService)).thenReturn(Observable.just(new Response<>(mockLoginActivity, oAuthTokenFactory.getValidToken())));

        presenter.beginLogin();

        verify(view).getActivity();
        verify(sharedPrefsManager).storeOAuthToken(any(OAuth1AccessToken.class));
        verify(view).finishActivityLaunchMain();
    }

    @Test
    public void startOAuthServiceError_viewDisplaysError()
    {
        LoginActivity mockLoginActivity = mock(LoginActivity.class);
        when(view.getActivity()).thenReturn(mockLoginActivity);
        when(rxSocialConnectWrapper.with(mockLoginActivity, oAuth10aService)).thenReturn(Observable.error(new Throwable()));

        presenter.beginLogin();

        verify(view).getActivity();
        verify(view).displayErrorDialog();
    }
}
