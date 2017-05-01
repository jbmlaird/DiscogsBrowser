package bj.rxjavaexperimentation.artist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.thefinestartist.finestwebview.FinestWebView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.artistreleases.ArtistReleasesActivity;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.common.MyRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 07/04/2017.
 */
public class ArtistActivity extends BaseActivity implements ArtistContract.View
{
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.recyclerView) MyRecyclerView rvDetailed;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject ArtistPresenter presenter;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        DaggerArtistComponent.builder()
                .appComponent(appComponent)
                .artistModule(new ArtistModule(this))
                .build()
                .inject(this);
    }

    public static Intent createIntent(Context context, String title, String id)
    {
        Intent intent = new Intent(context, ArtistActivity.class);
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
        presenter.setupRecyclerView(this, rvDetailed, getIntent().getStringExtra("title"));
        presenter.getData(getIntent().getStringExtra("id"));
    }

    @Override
    public void showMemberDetails(String name, String id)
    {
        startActivity(createIntent(this, name, id));
    }

    @Override
    public void showLink(String link)
    {
        new FinestWebView.Builder(this).show(link);
    }

    @Override
    public void showArtistReleases(String title, String id)
    {
        startActivity(ArtistReleasesActivity.createIntent(this, title, id));
    }
}
