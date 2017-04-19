package bj.rxjavaexperimentation.singlelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
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
    @BindView(R.id.ivLoading) ImageView ivLoading;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.etFilter) EditText etFilter;
    @Inject SingleListPresenter presenter;
    @Inject ImageViewAnimator imageViewAnimator;

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        SingleListComponent component = DaggerSingleListComponent.builder()
                .appComponent(appComponent)
                .singleListModule(new SingleListModule(this))
                .build();

        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list);
        ButterKnife.bind(this);
        imageViewAnimator.rotateImage(ivLoading);
        setupToolbar(toolbar);
        presenter.setupSubscription();
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
}
