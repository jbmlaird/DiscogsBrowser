package bj.vinylbrowser.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.home.HomeController;
import bj.vinylbrowser.login.LoginActivity;
import bj.vinylbrowser.search.SearchController;
import bj.vinylbrowser.utils.SharedPrefsManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The view the user sees after logging in.
 */
public class MainActivity extends BaseActivity implements MainContract.View
{
    @BindView(R.id.lytContent) FrameLayout lytContent;
    @Inject SharedPrefsManager sharedPrefsManager;
    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!sharedPrefsManager.isUserLoggedIn())
        {
            startActivity(LoginActivity.createIntent(this));
            finish();
        }
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        router = Conductor.attachRouter(this, lytContent, savedInstanceState);
        HomeController homeController = new HomeController();
        homeController.setRetainViewMode(Controller.RetainViewMode.RETAIN_DETACH);
        if (!router.hasRootController())
        {
            router.setRoot(RouterTransaction.with(homeController));
        }
    }

    public void onSearch(MenuItem menuItem)
    {
        router.pushController(RouterTransaction.with(new SearchController())
                .popChangeHandler(new FadeChangeHandler())
                .pushChangeHandler(new FadeChangeHandler()));
    }

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .firstComponentBuilder()
                .firstActivityModule(new MainModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context)
    {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onBackPressed()
    {
        if (!router.handleBack())
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        router.popCurrentController();
        return true;
    }

    public Router getRouter()
    {
        return router;
    }
}
