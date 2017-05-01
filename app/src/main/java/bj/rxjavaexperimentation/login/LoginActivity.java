package bj.rxjavaexperimentation.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;

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

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        LoginComponent component = DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build();

        component.inject(this);
    }

    public static Intent createIntent(Context context)
    {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        if (presenter.hasUserLoggedIn())
        {
            finish();
        }
    }

    @OnClick(R.id.btnLogin)
    public void loginTapped()
    {
        presenter.startOAuthService(this);
    }

    @OnClick(R.id.tvTnCs)
    public void onTsnCsClicked()
    {
        new MaterialDialog.Builder(this)
                .title("Privacy Policy")
                .negativeText("Back")
                .content(R.string.privacy_policy)
                .show();
    }

    @Override
    public void finish()
    {
        startActivity(MainActivity.createIntent(this));
        super.finish();
    }
}
