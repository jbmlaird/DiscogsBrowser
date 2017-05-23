package bj.vinylbrowser.artist;

import android.content.Context;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.rxmodifiers.RemoveUnwantedLinksFunction;
import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.LogWrapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 07/04/2017.
 */
@Module
public class ArtistModule
{
    private ArtistContract.View view;

    public ArtistModule(ArtistContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    protected ArtistContract.View providesDetailedView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected ArtistController provideArtistController(Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new ArtistController(view, context, imageViewAnimator, tracker);
    }

    @Provides
    protected RemoveUnwantedLinksFunction provideRemoveUnwantedLinksFunction()
    {
        return new RemoveUnwantedLinksFunction();
    }

    @Provides
    @ActivityScope
    protected ArtistPresenter provideArtistPresenter(DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                                     LogWrapper log, ArtistController controller, RemoveUnwantedLinksFunction function)
    {
        return new ArtistPresenter(view, discogsInteractor, mySchedulerProvider, log, controller, function);
    }
}
