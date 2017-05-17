package bj.discogsbrowser.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.thefinestartist.finestwebview.FinestWebView;

import javax.inject.Inject;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.R;
import bj.discogsbrowser.common.BaseActivity;
import bj.discogsbrowser.customviews.MyRecyclerView;
import bj.discogsbrowser.epoxy.common.BaseController;
import bj.discogsbrowser.release.ReleaseActivity;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
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
        presenter.getReleaseAndLabelDetails(getIntent().getStringExtra("id"));
    }

    @Override
    protected void onResume()
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.loaded), "onResume", 1L);
        super.onResume();
    }

    @Override
    public void displayRelease(String id, String title)
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.clicked), "labelRelease", 1L);
        startActivity(ReleaseActivity.createIntent(this, title, id));
    }

    @Override
    public void openLink(String uri)
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.clicked), uri, 1L);
        new FinestWebView.Builder(this).show(uri);
    }

    @Override
    public void retry()
    {
        tracker.send(getString(R.string.label_activity), getString(R.string.label_activity), getString(R.string.clicked), "retry", 1L);
        presenter.getReleaseAndLabelDetails(getIntent().getStringExtra("id"));
    }

    private void setupRecyclerView(MyRecyclerView recyclerView, BaseController controller, String title)
    {
        super.setupRecyclerView(recyclerView, controller);
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
