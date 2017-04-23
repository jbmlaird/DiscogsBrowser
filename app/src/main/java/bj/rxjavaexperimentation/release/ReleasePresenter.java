package bj.rxjavaexperimentation.release;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class ReleasePresenter implements ReleaseContract.Presenter
{
    private ReleaseContract.View mView;

    @Inject
    public ReleasePresenter(ReleaseContract.View mView)
    {
        this.mView = mView;
    }
}
