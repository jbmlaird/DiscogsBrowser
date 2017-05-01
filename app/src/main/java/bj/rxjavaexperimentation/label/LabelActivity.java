package bj.rxjavaexperimentation.label;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.thefinestartist.finestwebview.FinestWebView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.common.MyRecyclerView;
import bj.rxjavaexperimentation.release.ReleaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class LabelActivity extends BaseActivity implements LabelContract.View
{
    @Inject LabelPresenter presenter;
    @BindView(R.id.recyclerView) MyRecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        DaggerLabelComponent.builder()
                .appComponent(appComponent)
                .labelModule(new LabelModule(this))
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
        presenter.setupRecyclerView(this, recyclerView, getIntent().getStringExtra("title"));
        presenter.getData(getIntent().getStringExtra("id"));
    }

    @Override
    public void displayRelease(String id, String title)
    {
        startActivity(ReleaseActivity.createIntent(this, title, id));
    }

    @Override
    public void openLink(String uri)
    {
        new FinestWebView.Builder(this).show(uri);
    }
}
