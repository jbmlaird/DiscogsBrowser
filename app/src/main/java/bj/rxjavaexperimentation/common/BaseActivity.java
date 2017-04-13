package bj.rxjavaexperimentation.common;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import bj.rxjavaexperimentation.App;
import bj.rxjavaexperimentation.AppComponent;
import bj.rxjavaexperimentation.R;
import icepick.Icepick;

/**
 * Created by j on 18/02/2017.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setupComponent(App.appComponent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    public abstract void setupComponent(AppComponent appComponent);

    public void setupActionBar(Toolbar toolbar)
    {
        // TODO: Fix
        toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
