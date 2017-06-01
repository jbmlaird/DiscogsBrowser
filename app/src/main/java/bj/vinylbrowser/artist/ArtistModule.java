package bj.vinylbrowser.artist;

import android.content.Context;

import bj.vinylbrowser.di.scopes.FragmentScope;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.rxmodifiers.RemoveUnwantedLinksFunction;
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
    @FragmentScope
    protected ArtistContract.View providesDetailedView()
    {
        return view;
    }

    @Provides
    @FragmentScope
    protected ArtistEpxController provideArtistController(Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new ArtistEpxController(view, context, imageViewAnimator, tracker);
    }

    @Provides
    protected RemoveUnwantedLinksFunction provideRemoveUnwantedLinksFunction()
    {
        return new RemoveUnwantedLinksFunction();
    }

    @Provides
    @FragmentScope
    protected ArtistPresenter provideArtistPresenter(DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                                     LogWrapper log, ArtistEpxController controller, RemoveUnwantedLinksFunction function)
    {
        return new ArtistPresenter(view, discogsInteractor, mySchedulerProvider, log, controller, function);
    }
}
