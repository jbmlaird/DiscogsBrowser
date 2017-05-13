package bj.discogsbrowser.artist;

import android.content.Context;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.rxmodifiers.RemoveUnwantedLinksFunction;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;
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
    ArtistContract.View providesDetailedView()
    {
        return view;
    }

    @Provides
    ArtistController provideArtistController(Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        return new ArtistController(view, context, imageViewAnimator, tracker);
    }

    @Provides
    RemoveUnwantedLinksFunction provideRemoveUnwantedLinksFunction()
    {
        return new RemoveUnwantedLinksFunction();
    }

    @Provides
    ArtistPresenter provideArtistPresenter(DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                           LogWrapper log, ArtistController controller, RemoveUnwantedLinksFunction function)
    {
        return new ArtistPresenter(view, discogsInteractor, mySchedulerProvider, log, controller, function);
    }
}
