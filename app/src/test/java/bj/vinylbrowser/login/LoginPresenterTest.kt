package bj.vinylbrowser.login

import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.wrappers.RxSocialConnectWrapper
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.oauth.OAuth10aService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.fuckboilerplate.rx_social_connect.Response
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {
    lateinit var presenter: LoginPresenter
    val view: LoginContract.View = mock()
    val sharedPrefsManager: SharedPrefsManager = mock()
    val rxSocialConnectWrapper: RxSocialConnectWrapper = mock()
    val oAuth10aService: OAuth10aService = mock()

    @Before
    fun setUp() {
        presenter = LoginPresenter(view, sharedPrefsManager, rxSocialConnectWrapper, oAuth10aService)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, sharedPrefsManager, oAuth10aService)
    }

    @Test
    fun startOAuthServiceValid_logsIn() {
        val mockLoginActivity: LoginActivity = mock()
        whenever(view.activity).thenReturn(mockLoginActivity)
        whenever(rxSocialConnectWrapper.with(mockLoginActivity, oAuth10aService)).thenReturn(Observable.just(Response<LoginActivity, OAuth1AccessToken>(mockLoginActivity, OAuthTokenFactory.getValidToken())))

        presenter.beginLogin()

        verify(view).activity
        verify(sharedPrefsManager).storeOAuthToken(any(OAuth1AccessToken::class.java))
        verify(view).finishActivityLaunchMain()
    }

    @Test
    fun startOAuthServiceError_viewDisplaysError() {
        val mockLoginActivity: LoginActivity = mock()
        whenever(view.activity).thenReturn(mockLoginActivity)
        whenever(rxSocialConnectWrapper.with(mockLoginActivity, oAuth10aService)).thenReturn(Observable.error<Response<LoginActivity, OAuth1AccessToken>>(Throwable()))

        presenter.beginLogin()

        verify(view).activity
        verify(view).displayErrorDialog()
    }
}