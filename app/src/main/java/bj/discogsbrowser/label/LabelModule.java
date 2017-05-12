package bj.discogsbrowser.label;

import android.content.Context;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;
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
    LabelContract.View provideLabelView()
    {
        return mView;
    }

}
