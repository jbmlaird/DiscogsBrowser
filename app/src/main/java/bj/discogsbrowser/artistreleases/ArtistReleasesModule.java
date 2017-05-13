package bj.discogsbrowser.artistreleases;

import android.content.Context;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.rxmodifiers.ArtistReleasesTransformer;
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
    ArtistReleasesContract.View providesView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected BehaviorRelay<List<ArtistRelease>> provideBehaviorRelay()
    {
        return BehaviorRelay.create();
    }

    @Provides
    @ActivityScope
    ArtistReleasesTransformer provideTransformer()
    {
        return new ArtistReleasesTransformer();
    }

    @Provides
    ArtistReleasesController provideArtistReleasesController(Context context, ImageViewAnimator imageViewAnimator)
    {
        return new ArtistReleasesController(context, view, imageViewAnimator);
    }

    @Provides
    @ActivityScope
    ArtistReleasesPresenter provideArtistReleasesPresenter(DiscogsInteractor discogsInteractor, ArtistReleasesController controller, BehaviorRelay<List<ArtistRelease>> behaviorRelay,
                                                           MySchedulerProvider mySchedulerProvider, ArtistReleasesTransformer artistReleasesTransformer)
    {
        return new ArtistReleasesPresenter(view, discogsInteractor, controller, behaviorRelay, mySchedulerProvider, artistReleasesTransformer);
    }
}
