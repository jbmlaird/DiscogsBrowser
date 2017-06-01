package bj.vinylbrowser.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.pedrovgs.DraggablePanel;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.login.LoginActivity;
import bj.vinylbrowser.main.panel.YouTubeListFragment;
import bj.vinylbrowser.main.panel.YouTubeListModule;
import bj.vinylbrowser.main.panel.YouTubePlayerHolder;
import bj.vinylbrowser.search.SearchController;
import bj.vinylbrowser.utils.SharedPrefsManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The view the user sees after logging in.
 */
public class MainActivity extends BaseActivity implements MainContract.View
{
    public MainComponent mainComponent;
    @BindView(R.id.lytContent) FrameLayout lytContent;
    @BindView(R.id.draggable_panel) DraggablePanel draggablePanel;
    @Inject MainPresenter mainPresenter;
    @Inject SharedPrefsManager sharedPrefsManager;
    @Inject YouTubePlayerHolder youTubePlayerHolder;
    @Inject YouTubeListFragment youTubeListFragment;
    @Inject RouterAttacher routerAttacher;
    private Router router;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        mainComponent = appComponent
                .mainComponentBuilder()
                .mainActivityModule(new MainModule(this))
                .build();
        mainComponent.inject(this);

        mainComponent
                .youTubeListComponentBuilder()
                .youTubeListModule(new YouTubeListModule(youTubeListFragment))
                .build()
                .inject(youTubeListFragment);
    }

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

        initialiseDraggablePanel();
        router = Conductor.attachRouter(this, lytContent, savedInstanceState);
        routerAttacher.attachRoot(router);
    }

    public void onSearch(MenuItem menuItem)
    {
        router.pushController(RouterTransaction.with(new SearchController())
                .popChangeHandler(new FadeChangeHandler())
                .pushChangeHandler(new FadeChangeHandler())
                .tag("SearchController"));
    }

    public static Intent createIntent(Context context)
    {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onBackPressed()
    {
        if (draggablePanel.isMaximized())
            draggablePanel.minimize();
        else if (!router.handleBack())
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (getCurrentFocus() != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        router.popCurrentController();
        return true;
    }

    public Router getRouter()
    {
        return router;
    }

    @Override
    public void initialiseDraggablePanel()
    {
        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(youTubePlayerHolder.buildYouTubeFragment());
        draggablePanel.setBottomFragment(youTubeListFragment);
        draggablePanel.setClickToMaximizeEnabled(true);
        draggablePanel.setEnableHorizontalAlphaEffect(true);
        draggablePanel.setTopFragmentMarginBottom(16);
        draggablePanel.setTopFragmentMarginRight(16);
        draggablePanel.initializeView();
        draggablePanel.minimize();
    }

    @Override
    public void displayDraggablePanel()
    {
        draggablePanel.setVisibility(View.VISIBLE);
        minimiseDraggablePanel();
    }

    @Override
    public void minimiseDraggablePanel()
    {
        draggablePanel.minimize();
    }
}
