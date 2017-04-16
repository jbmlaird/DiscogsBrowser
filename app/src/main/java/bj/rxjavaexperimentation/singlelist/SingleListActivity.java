package bj.rxjavaexperimentation.singlelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Josh Laird on 12/04/2017.
 * <p>
 * Activity to display search information in just one column (rather than 3 fragments like {@link bj.rxjavaexperimentation.artistreleases.ArtistReleasesActivity}).
 * <p>
 * TODO: Refactor
 */

public class SingleListActivity extends BaseActivity implements SingleListContract.View
{
    @Inject SingleListPresenter presenter;
    @BindView(R.id.etFilter) EditText etFilter;

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
        presenter.getData(getIntent().getStringExtra("type"), getIntent().getStringExtra("username"));
    }

    @Override
    public Observable<CharSequence> filterIntent()
    {
        return RxTextView.textChanges(etFilter)
                .debounce(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .filter(textChangeEvent -> textChangeEvent.length() > 2);
    }
}
