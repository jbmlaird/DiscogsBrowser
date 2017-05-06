package bj.discogsbrowser.common;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by Josh Laird on 27/04/2017.
 */

public class BaseFragment extends Fragment
{
    protected Unbinder unbinder;

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }
}
