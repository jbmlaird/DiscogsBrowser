package bj.discogsbrowser.singlelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.artist.ArtistActivity;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.label.LabelActivity;
import bj.discogsbrowser.marketplace.MarketplaceListingActivity;
import bj.discogsbrowser.master.MasterActivity;
import bj.discogsbrowser.order.OrderActivity;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 12/04/2017.
 * <p>
 * Activity to display search information in just one column (rather than 3 fragments like {@link bj.discogsbrowser.artistreleases.ArtistReleasesActivity}).
 */
public class SingleListActivity extends BaseActivity implements SingleListContract.View
{
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

    public static Intent createIntent(Context context, Integer stringId, String username)
    {
        Intent intent = new Intent(context, SingleListActivity.class);
        intent.putExtra("type", stringId);
        intent.putExtra("username", username);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        presenter.setupFilterSubscription();
        presenter.setupRecyclerView(this, recyclerView);
        presenter.getData(getIntent().getIntExtra("type", -1), getIntent().getStringExtra("username"));
    }

    @Override
    public Observable<CharSequence> filterIntent()
    {
        return RxTextView.textChanges(etFilter);
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
            case "listing":
                startActivity(MarketplaceListingActivity.createIntent(this, title, id, "", ""));
                break;
            case "order":
                startActivity(OrderActivity.createIntent(this, id));
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
