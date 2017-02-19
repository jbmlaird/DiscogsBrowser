package bj.rxjavaexperimentation.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arlib.floatingsearchview.FloatingSearchView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View
{
    @BindView(R.id.pbRecyclerView) ProgressBar pbRecyclerView;
    @BindView(R.id.rvResults) RecyclerView rvResults;
    @BindView(R.id.floating_search_view) FloatingSearchView floatingSearchView;
    @Inject MainPresenter presenter;
    private MainComponent mainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.setView(this);
        presenter.setupRecyclerView(rvResults);
        floatingSearchView.setOnSearchListener(presenter);
    }

    @Override
    public void setupComponent(AppComponent appComponent)
    {
        mainComponent = DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build();

        mainComponent.inject(this);
    }

    @Override
    public void hideProgressBar()
    {
        pbRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar()
    {
        pbRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public AppCompatActivity getActivity()
    {
        return getActivity();
    }
}
