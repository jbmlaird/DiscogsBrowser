package bj.discogsbrowser.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.customviews.MyRecyclerView;
import bj.discogsbrowser.login.LoginActivity;
import bj.discogsbrowser.marketplace.MarketplaceListingActivity;
import bj.discogsbrowser.order.OrderActivity;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.search.SearchActivity;
import bj.discogsbrowser.singlelist.SingleListActivity;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The view the user sees after logging in.
 */
public class MainActivity extends BaseActivity implements MainContract.View
{
    @BindView(R.id.ivLoading) ImageView ivLoading;
    @BindView(R.id.lytLoading) ConstraintLayout lytLoading;
    @BindView(R.id.lytError) ConstraintLayout lytError;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lytMainContent) LinearLayout lytMainContent;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @Inject ImageViewAnimator imageViewAnimator;
    @Inject MainPresenter presenter;
    @Inject SharedPrefsManager sharedPrefsManager;
    @Inject AnalyticsTracker tracker;
    @Inject MainController controller;
    private Drawer drawer;

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
        toolbar.inflateMenu(R.menu.options_menu);
        toolbar.setTitle("Home");
    }

    public void onSearch(MenuItem menuItem)
    {
        startActivity(SearchActivity.createIntent(this));
    }

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .mainComponentBuilder()
                .mainActivityModule(new MainModule(this))
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
        if (drawer != null && drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
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
    public void showLoading(boolean b)
    {
        if (b)
        {
            lytMainContent.setVisibility(View.GONE);
            imageViewAnimator.rotateImage(ivLoading);
            lytLoading.setVisibility(View.VISIBLE);
            displayError(false);
        }
        else
        {
            lytMainContent.setVisibility(View.VISIBLE);
            ivLoading.clearAnimation();
            lytLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public MainActivity getActivity()
    {
        return this;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.loaded), "onResume", 1L);
        if (drawer == null)
            presenter.connectAndBuildNavigationDrawer(toolbar);
        presenter.buildRecommendations();
        presenter.buildViewedReleases();
    }

    @Override
    public void retryHistory()
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.error), getString(R.string.clicked), "retryHistory", 1L);
        presenter.buildViewedReleases();
    }

    @Override
    public void retryRecommendations()
    {
        presenter.showLoadingRecommendations(true);
        tracker.send(getString(R.string.main_activity), getString(R.string.error), getString(R.string.clicked), "retryRecommendations", 1L);
        presenter.buildRecommendations();
    }

    @Override
    public void setDrawer(Drawer drawer)
    {
        this.drawer = drawer;
        displayError(false);
        if (!sharedPrefsManager.isOnBoardingCompleted())
            new TapTargetSequence(this)
                    .targets(
                            TapTarget.forToolbarMenuItem(toolbar, R.id.search, "Search Discogs here", "This is where the magic happens")
                                    .targetCircleColor(R.color.colorAccent)
                                    .cancelable(false),
                            TapTarget.forToolbarNavigationIcon(toolbar, "Navigation Drawer", "Open this to view your Wantlist and Collection")
                                    .targetCircleColor(R.color.colorAccent)
                                    .cancelable(false))
                    .listener(new TapTargetSequence.Listener()
                    {
                        @Override
                        public void onSequenceFinish()
                        {
                            drawer.openDrawer();
                            sharedPrefsManager.setOnboardingCompleted(getString(R.string.onboarding_completed));
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean ye)
                        {
                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget)
                        {
                        }
                    }).start();
    }

    /**
     * RecyclerView gets detached upon attaching the navigation drawer so must be called from the
     * {@link MainPresenter} after fetching login details.
     */
    @Override
    public void setupRecyclerView()
    {
        setupRecyclerView(recyclerView, controller);
        controller.requestModelBuild();
        showLoading(false);
    }

    @Override
    public void displayOrder(String id)
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.clicked), "order", 1L);
        startActivity(OrderActivity.createIntent(this, id));
    }

    @Override
    public void displayOrdersActivity(String username)
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.clicked), "All orders", 1L);
        startActivity(SingleListActivity.createIntent(this, R.string.orders, username));
    }

    @Override
    public void displayListing(String listingId, String title, String username, String artist, String seller)
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.clicked), "listing", 1L);
        startActivity(MarketplaceListingActivity.createIntent(this, listingId, title, artist, seller));
    }

    @Override
    public void displayListingsActivity(String username)
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.clicked), "All listings", 1L);
        startActivity(SingleListActivity.createIntent(this, R.string.selling, username));
    }

    @Override
    public void displayError(boolean b)
    {
        if (b)
        {
            showLoading(false);
            lytError.setVisibility(View.VISIBLE);
        }
        else
            lytError.setVisibility(View.GONE);
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.main_activity), getString(R.string.clicked), "Retry", 1L);
        presenter.retry();
    }

    @Override
    public void displayRelease(String releaseName, String id)
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.recently_viewed_release), getString(R.string.clicked), releaseName, 1L);
        startActivity(ReleaseActivity.createIntent(this, releaseName, id));
    }

    @Override
    public void learnMore()
    {
        tracker.send(getString(R.string.main_activity), getString(R.string.learn_more), getString(R.string.clicked), "recommendations learn more", 1L);
        new MaterialDialog.Builder(this)
                .content(getString(R.string.learn_more_content))
                .negativeText("Dismiss")
                .show();
    }

    @OnClick(R.id.btnError)
    public void reconnectPressed()
    {
        presenter.connectAndBuildNavigationDrawer(toolbar);
    }
}
