package bj.rxjavaexperimentation.main;

import dagger.Module;

/**
 * Created by j on 18/02/2017.
 */

@Module
public class MainModule
{
    private MainContract.View mView;

    public MainModule(MainContract.View view)
    {
        mView = view;
    }
}
