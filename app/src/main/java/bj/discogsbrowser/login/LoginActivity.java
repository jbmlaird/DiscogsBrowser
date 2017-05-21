package bj.discogsbrowser.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.main.MainActivity;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josh Laird on 15/04/2017.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View
{
    @BindView(R.id.tvTnCs) TextView tvTnCs;
    @Inject LoginPresenter presenter;
    @Inject AnalyticsTracker tracker;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .loginComponentBuilder()
                .loginActivityModule(new LoginModule(this))
                .build()
                .inject(this);
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
        SpannableString content = new SpannableString(getString(R.string.by_signing_in_you_agree_to_the_privacy_policy));
        content.setSpan(new UnderlineSpan(), content.length() - 14, content.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvTnCs.setText(content);
    }

    @OnClick(R.id.btnLogin)
    public void loginTapped()
    {
        tracker.send(getString(R.string.login_activity), getString(R.string.login_activity), getString(R.string.clicked), "login", 1);
        presenter.beginLogin();
    }

    @OnClick(R.id.tvTnCs)
    public void onTsnCsClicked()
    {
        tracker.send(getString(R.string.login_activity), getString(R.string.login_activity), getString(R.string.clicked), "privacy policy", 1);
        new MaterialDialog.Builder(this)
                .title("Privacy Policy")
                .negativeText("Dismiss")
                .content(R.string.privacy_policy)
                .show();
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.login_activity), getString(R.string.login_activity), getString(R.string.loaded), "onResume", 1);
        super.onResume();
    }

    @Override
    public void finishActivityLaunchMain()
    {
        Intent intent = MainActivity.createIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        new MaterialDialog.Builder(this)
                .title("Quit")
                .content("Really quit?")
                .negativeText("Cancel")
                .positiveText("Quit")
                .onPositive((dialog, which) ->
                {
                    dialog.dismiss();
                    finishAndRemoveTask();
                })
                .show();
    }

    @Override
    public void displayErrorDialog()
    {
        new MaterialDialog.Builder(this)
                .title("Error")
                .content("Unable to contact Discogs. Please try again.")
                .negativeText("Dismiss")
                .show();
    }

    @Override
    public LoginActivity getActivity()
    {
        return this;
    }
}
