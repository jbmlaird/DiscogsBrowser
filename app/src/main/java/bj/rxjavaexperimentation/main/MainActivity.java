package bj.rxjavaexperimentation.main;

import android.os.Bundle;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View
{
    @BindView(R.id.floating_search_view) FloatingSearchView floatingSearchView;
    @BindView(R.id.tvSuccessText) TextView tvSuccessText;
    @Inject MainPresenter presenter;
    private MainComponent mainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.setView(this);
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
    public void setSuccessText(String successText)
    {
        tvSuccessText.setText(successText);
    }
}
