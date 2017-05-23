package bj.vinylbrowser.label;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import bj.vinylbrowser.AppComponent;
import bj.vinylbrowser.R;
import bj.vinylbrowser.common.BaseActivity;
import bj.vinylbrowser.customviews.MyRecyclerView;
import bj.vinylbrowser.epoxy.common.BaseController;
import bj.vinylbrowser.release.ReleaseActivity;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class LabelActivity extends BaseActivity implements LabelContract.View
{
    @Inject LabelPresenter presenter;
    @Inject AnalyticsTracker tracker;
    @Inject LabelController controller;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        appComponent
                .labelComponentBuilder()
                .labelActivityModule(new LabelModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String title, String id)
    {
        Intent intent = new Intent(context, LabelActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar);
        setupRecyclerView(recyclerView, controller, getIntent().getStringExtra("title"));
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.loaded), "onResume", "1");
        super.onResume();
    }

    @Override
    public void displayRelease(String id, String title)
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.clicked), "labelRelease", "1");
        startActivity(ReleaseActivity.createIntent(this, title, id));
    }

    @Override
    public void openLink(String uri)
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.clicked), uri, "1");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.clicked), "retry", "1");
        presenter.fetchReleaseDetails(getIntent().getStringExtra("id"));
    }

    private void setupRecyclerView(MyRecyclerView recyclerView, BaseController controller, String title)
    {
        super.setupRecyclerView(recyclerView, controller);
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
