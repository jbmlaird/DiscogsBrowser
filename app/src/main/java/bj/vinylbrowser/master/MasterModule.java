package bj.vinylbrowser.master;

import android.content.Context;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.ArtistsBeautifier;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    protected MasterEpxController provideController(Context context, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new MasterEpxController(view, context, artistsBeautifier, imageViewAnimator, tracker);
    }

    @Provides
    @ActivityScope
    protected MasterPresenter providePresenter(DiscogsInteractor interactor, MasterEpxController controller, MySchedulerProvider mySchedulerProvider)
    {
        return new MasterPresenter(interactor, controller, mySchedulerProvider);
    }
}
