package bj.vinylbrowser.label;

import android.content.Context;

import bj.vinylbrowser.di.scopes.FragmentScope;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    @FragmentScope
    protected LabelContract.View provideLabelView()
    {
        return mView;
    }

    @Provides
    @FragmentScope
    protected LabelEpxController provideLabelController(Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new LabelEpxController(context, mView, imageViewAnimator, tracker);
    }

    @Provides
    @FragmentScope
    protected LabelPresenter provideLabelPresenter(DiscogsInteractor interactor, LabelEpxController controller, MySchedulerProvider mySchedulerProvider)
    {
        return new LabelPresenter(controller, interactor, mySchedulerProvider);
    }
}
