package bj.vinylbrowser.artistreleases;

import android.content.Context;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@Module
public class ArtistReleasesModule
{
    private ArtistReleasesContract.View view;

    public ArtistReleasesModule(ArtistReleasesContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    protected ArtistReleasesContract.View providesView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected ArtistReleaseBehaviorRelay provideBehaviorRelay()
    {
        return new ArtistReleaseBehaviorRelay();
    }

    @Provides
    @ActivityScope
    protected ArtistReleasesTransformer provideTransformer()
    {
        return new ArtistReleasesTransformer();
    }

    @Provides
    protected ArtistReleasesController provideArtistReleasesController(Context context, ImageViewAnimator imageViewAnimator)
    {
        return new ArtistReleasesController(context, view, imageViewAnimator);
    }

    @Provides
    @ActivityScope
    protected ArtistReleasesPresenter provideArtistReleasesPresenter(DiscogsInteractor discogsInteractor, ArtistReleasesController controller, ArtistReleaseBehaviorRelay behaviorRelay,
                                                                     MySchedulerProvider mySchedulerProvider, ArtistReleasesTransformer artistReleasesTransformer)
    {
        return new ArtistReleasesPresenter(view, discogsInteractor, controller, behaviorRelay, mySchedulerProvider, artistReleasesTransformer);
    }
}
