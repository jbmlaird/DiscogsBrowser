package bj.rxjavaexperimentation.main;

import android.content.Context;

import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

import bj.rxjavaexperimentation.discogs.DiscogsInteractor;

/**
 * Created by j on 18/02/2017.
 */
public class MainPresenter implements MainContract.Presenter
{
    private static final String TAG = "MainPresenter";
    private Context mContext;
    private MainContract.View mView;
    private DiscogsInteractor mInteractor;
    private Drawer navigationDrawer;

    @Inject
    public MainPresenter(DiscogsInteractor interactor)
    {
        mInteractor = interactor;
    }

    @Override
    public void setView(MainContract.View view)
    {
        mContext = view.getActivity();
        mView = view;
    }
}
