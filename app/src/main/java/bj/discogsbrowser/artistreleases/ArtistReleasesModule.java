package bj.discogsbrowser.artistreleases;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Singleton;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 10/04/2017.
 */

@Module
class ArtistReleasesModule
{
    private ArtistReleasesContract.View view;

    public ArtistReleasesModule(ArtistReleasesContract.View view)
    {
        this.view = view;
    }

    @Provides
    @Singleton
    ArtistReleasesContract.View providesView()
    {
        return view;
    }

    @Provides
    @Singleton
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    BehaviorRelay<List<ArtistRelease>> provideBehaviorRelay()
    {
        return BehaviorRelay.create();
    }
}
