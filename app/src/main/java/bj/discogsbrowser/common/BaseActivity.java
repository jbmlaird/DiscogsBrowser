package bj.discogsbrowser.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import bj.discogsbrowser.App;
import bj.discogsbrowser.AppComponent;
import butterknife.Unbinder;

/**
 * Created by j on 18/02/2017.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupComponent(App.appComponent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    public abstract void setupComponent(AppComponent appComponent);

    protected void setupToolbar(Toolbar toolbar, String title)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected void setupToolbar(Toolbar toolbar)
    {
        setupToolbar(toolbar, "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }
}
