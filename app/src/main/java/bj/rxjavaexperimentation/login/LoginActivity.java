package bj.rxjavaexperimentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.main.MainActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josh Laird on 15/04/2017.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View
{
    @Inject LoginPresenter presenter;
    private static final String TAG = "LoginActivity";

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        LoginComponent component = DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build();

        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (presenter.hasUserLoggedIn())
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btnLogin)
    public void loginTapped()
    {
        presenter.startOAuthService(this);
    }

    @Override
    public void finish()
    {
        startActivity(new Intent(this, MainActivity.class));
        super.finish();
    }
}
