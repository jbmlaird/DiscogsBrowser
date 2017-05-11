package bj.discogsbrowser.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import bj.discogsbrowser.App;
import bj.discogsbrowser.AppComponent;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 27/04/2017.
 */

public abstract class BaseFragment extends Fragment
{
    protected Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupComponent(App.appComponent);
    }

    public abstract void setupComponent(AppComponent appComponent);

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }
}
