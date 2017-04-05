package bj.rxjavaexperimentation.main;

import android.content.Context;

import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

/**
 * Created by j on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private static final String TAG = "MainPresenter";
    private Context mContext;
    private MainContract.View mView;
    private Drawer navigationDrawer;

    @Inject
    public MainPresenter(MainContract.View view)
    {
        mView = view;
    }
}
