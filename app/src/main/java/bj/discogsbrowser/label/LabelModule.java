package bj.discogsbrowser.label;

import android.content.Context;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.di.scopes.ActivityScope;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Module
public class LabelModule
{
    private LabelContract.View mView;

    public LabelModule(LabelContract.View view)
    {
        mView = view;
    }

    @Provides
    @ActivityScope
    protected LabelContract.View provideLabelView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    protected LabelController provideLabelController(Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new LabelController(context, mView, imageViewAnimator, tracker);
    }

    @Provides
    @ActivityScope
    protected LabelPresenter provideLabelPresenter(DiscogsInteractor interactor, LabelController controller, MySchedulerProvider mySchedulerProvider)
    {
        return new LabelPresenter(controller, interactor, mySchedulerProvider);
    }
}
