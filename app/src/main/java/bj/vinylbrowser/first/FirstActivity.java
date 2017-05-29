package bj.vinylbrowser.first;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.login.LoginActivity;
import bj.vinylbrowser.main.MainController;
import bj.vinylbrowser.search.SearchController;
import bj.vinylbrowser.utils.SharedPrefsManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The view the user sees after logging in.
 */
public class FirstActivity extends BaseActivity implements FirstContract.View
{
    @BindView(R.id.lytContent) FrameLayout lytContent;
    @Inject SharedPrefsManager sharedPrefsManager;
    private Drawer drawer;
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
        MainController mainController = new MainController();
        mainController.setRetainViewMode(Controller.RetainViewMode.RETAIN_DETACH);
        if (!router.hasRootController())
        {
            router.setRoot(RouterTransaction.with(mainController));
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
                .firstActivityModule(new FirstModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context)
    {
        return new Intent(context, FirstActivity.class);
    }

    @Override
    public void onBackPressed()
    {
        if (!router.handleBack())
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        router.popCurrentController();
        return true;
//        return super.onOptionsItemSelected(item);
    }


    //    @Override
//    public void setDrawer(Drawer drawer)
//    {
//        this.drawer = drawer;
//        displayError(false);
//        if (!sharedPrefsManager.isOnBoardingCompleted())
//            new TapTargetSequence(this)
//                    .targets(
//                            TapTarget.forToolbarMenuItem(toolbar, R.id.search, "Search Discogs here", "This is where the magic happens")
//                                    .targetCircleColor(R.color.colorAccent)
//                                    .cancelable(false),
//                            TapTarget.forToolbarNavigationIcon(toolbar, "Navigation Drawer", "Open this to view your Wantlist and Collection")
//                                    .targetCircleColor(R.color.colorAccent)
//                                    .cancelable(false))
//                    .listener(new TapTargetSequence.Listener()
//                    {
//                        @Override
//                        public void onSequenceFinish()
//                        {
//                            drawer.openDrawer();
//                            sharedPrefsManager.setOnboardingCompleted(getString(R.string.onboarding_completed));
//                        }
//
//                        @Override
//                        public void onSequenceStep(TapTarget lastTarget, boolean ye)
//                        {
//                        }
//
//                        @Override
//                        public void onSequenceCanceled(TapTarget lastTarget)
//                        {
//                        }
//                    }).start();
//    }
}
