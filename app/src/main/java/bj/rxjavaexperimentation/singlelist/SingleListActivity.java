package bj.rxjavaexperimentation.singlelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artist.ArtistActivity;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.label.LabelActivity;
import bj.rxjavaexperimentation.marketplace.MarketplaceListingActivity;
import bj.rxjavaexperimentation.master.MasterActivity;
import bj.rxjavaexperimentation.order.OrderActivity;
import bj.rxjavaexperimentation.release.ReleaseActivity;
import bj.rxjavaexperimentation.utils.AnalyticsTracker;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 12/04/2017.
 * <p>
 * Activity to display search information in just one column (rather than 3 fragments like {@link bj.rxjavaexperimentation.artistreleases.ArtistReleasesActivity}).
 */
public class SingleListActivity extends BaseActivity implements SingleListContract.View
{
    @BindView(R.id.tvNoItems) TextView tvNoItems;
    @BindView(R.id.tvError) TextView tvError;

    @BindView(R.id.ivLoading) ImageView ivLoading;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.etFilter) EditText etFilter;
    @Inject SingleListPresenter presenter;
    @Inject ImageViewAnimator imageViewAnimator;
    @Inject AnalyticsTracker tracker;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        SingleListComponent component = DaggerSingleListComponent.builder()
                .appComponent(appComponent)
                .singleListModule(new SingleListModule(this))
                .build();

        component.inject(this);
    }

    public static Intent createIntent(Context context, String type, String username)
    {
        Intent intent = new Intent(context, SingleListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("username", username);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        unbinder = ButterKnife.bind(this);
        imageViewAnimator.rotateImage(ivLoading);
        setupToolbar(toolbar);
        presenter.setupFilterSubscription();
        presenter.setupRecyclerView(this, recyclerView);
        presenter.getData(getIntent().getStringExtra("type"), getIntent().getStringExtra("username"));
    }

    @Override
    public Observable<CharSequence> filterIntent()
    {
        return RxTextView.textChanges(etFilter);
    }

    @Override
    public void stopLoading()
    {
        ivLoading.setVisibility(View.GONE);
        ivLoading.clearAnimation();
    }

    @Override
    public void showNoItems(boolean showNoItems, String noItems)
    {
        if (showNoItems)
        {
            tvNoItems.setVisibility(View.VISIBLE);
            tvNoItems.setText(noItems);
            tvError.setVisibility(View.GONE);
            stopLoading();
        }
        else
            tvNoItems.setVisibility(View.GONE);
    }


    @Override
    public void showError(boolean showError, String error)
    {
        if (showError)
        {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(error);
            tvNoItems.setVisibility(View.GONE);
            stopLoading();
            tracker.send(getString(R.string.single_list_activity), getIntent().getStringExtra("type"), getString(R.string.error), getIntent().getStringExtra("type"), 1L);
        }
        else
            tvError.setVisibility(View.GONE);
    }

    @Override
    public void displayListing(String listingId, String title, String artist, String seller)
    {
        tracker.send(getString(R.string.single_list_activity), getIntent().getStringExtra("type"), getString(R.string.clicked), "displayListing", 1L);
        startActivity(MarketplaceListingActivity.createIntent(this, listingId, title, artist, seller));
    }

    @Override
    public void displayOrder(String id)
    {
        tracker.send(getString(R.string.single_list_activity), getIntent().getStringExtra("type"), getString(R.string.clicked), "displayOrder", 1L);
        startActivity(OrderActivity.createIntent(this, id));
    }

    @Override
    public void launchDetailedActivity(String type, String title, String id)
    {
        tracker.send(getString(R.string.single_list_activity), getIntent().getStringExtra("type"), getString(R.string.clicked), "detailedActivity" + type, 1L);
        switch (type)
        {
            case "release":
                startActivity(ReleaseActivity.createIntent(this, title, id));
                break;
            case "label":
                startActivity(LabelActivity.createIntent(this, title, id));
                break;
            case "artist":
                startActivity(ArtistActivity.createIntent(this, title, id));
                break;
            case "master":
                startActivity(MasterActivity.createIntent(this, title, id));
                break;
        }
    }

    @Override
    public Context getActivityContext()
    {
        return this;
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.single_list_activity), getIntent().getStringExtra("type"), getString(R.string.loaded), "onResume", 1L);
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.dispose();
    }
}
