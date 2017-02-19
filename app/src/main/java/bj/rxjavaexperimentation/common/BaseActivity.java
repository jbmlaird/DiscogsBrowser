package bj.rxjavaexperimentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import bj.rxjavaexperimentation.App;
import bj.rxjavaexperimentation.AppComponent;

/**
 * Created by j on 18/02/2017.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupComponent(App.appComponent);
    }

    public abstract void setupComponent(AppComponent appComponent);
}
