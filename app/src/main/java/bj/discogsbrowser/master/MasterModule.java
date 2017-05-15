package bj.discogsbrowser.master;

import android.content.Context;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Module
public class MasterModule
{
    private MasterContract.View view;

    public MasterModule(MasterContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    protected MasterContract.View provideMasterView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected MasterController provideController(Context context, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new MasterController(view, context, artistsBeautifier, imageViewAnimator, tracker);
    }

    @Provides
    @ActivityScope
    protected MasterPresenter providePresenter(DiscogsInteractor interactor, MasterController controller, MySchedulerProvider mySchedulerProvider)
    {
        return new MasterPresenter(interactor, controller, mySchedulerProvider);
    }
}
