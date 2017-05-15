package bj.discogsbrowser.artistreleases;

import android.content.Context;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
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
