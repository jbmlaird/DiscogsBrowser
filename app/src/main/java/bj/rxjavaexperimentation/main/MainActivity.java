package bj.rxjavaexperimentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.common.MyRecyclerView;
import bj.rxjavaexperimentation.marketplace.MarketplaceListingActivity;
import bj.rxjavaexperimentation.order.OrderActivity;
import bj.rxjavaexperimentation.search.SearchActivity;
import bj.rxjavaexperimentation.singlelist.SingleListActivity;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        showLoading(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
    }

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        MainComponent mainComponent = DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build();

        mainComponent.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getTitle().equals("Search"))
            startActivity(new Intent(this, SearchActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
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
                        super.onBackPressed();
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
        }
        else
        {
            lytMainContent.setVisibility(View.VISIBLE);
            ivLoading.clearAnimation();
            lytLoading.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (drawer == null)
            presenter.connectAndBuildNavigationDrawer(this, toolbar);
    }

    @Override
    public void setDrawer(Drawer drawer)
    {
        this.drawer = drawer;
        showLoading(false);
        displayError(false);
    }

    /**
     * RecyclerView gets detached upon attaching the navigation drawer.
     * <p>
     * Called from the View as it has a reference to the Activity and RecyclerView.
     */
    @Override
    public void setupRecyclerView()
    {
        presenter.setupRecyclerView(this, recyclerView);
    }

    @Override
    public void displayOrder(String id)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("orderId", id);
        startActivity(intent);
    }

    @Override
    public void displayOrdersActivity()
    {
        Intent intent = new Intent(this, SingleListActivity.class);
        intent.putExtra("type", "orders");
        startActivity(intent);
    }

    @Override
    public void displayListing(String listingId, String username)
    {
        Intent intent = new Intent(this, MarketplaceListingActivity.class);
        intent.putExtra("id", listingId);
        intent.putExtra("seller", username);
        startActivity(intent);
    }

    @Override
    public void displayError(boolean b)
    {
        showLoading(false);
        if (b)
            lytError.setVisibility(View.VISIBLE);
        else
            lytError.setVisibility(View.GONE);
    }

    @Override
    public void displayListingsActivity(String username)
    {
        Intent intent = new Intent(this, SingleListActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("type", "selling");
        startActivity(intent);
    }

    @OnClick(R.id.btnError)
    public void reconnectPressed()
    {
        presenter.connectAndBuildNavigationDrawer(this, toolbar);
    }
}
