package bj.rxjavaexperimentation.master;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class MasterPresenter implements MasterContract.Presenter
{
    private MasterContract.View mView;

    @Inject
    public MasterPresenter(MasterContract.View mView)
    {
        this.mView = mView;
    }
}
