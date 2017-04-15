package bj.rxjavaexperimentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.common.BaseActivity;
import bj.rxjavaexperimentation.search.SearchActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View
{
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject MainPresenter presenter;
    private MainComponent mainComponent;
    private Drawer result;

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        result = presenter.buildNavigationDrawer(this, toolbar);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getTitle().equals("Search"))
            startActivity(new Intent(this, SearchActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        // handle the back press :D close the drawer first
        if (result != null && result.isDrawerOpen())
        {
            result.closeDrawer();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
