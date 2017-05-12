package bj.discogsbrowser.artistreleases;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

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
    CompositeDisposable providesCompositeDisposable()
    {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    BehaviorRelay<List<ArtistRelease>> provideBehaviorRelay()
    {
        return BehaviorRelay.create();
    }
}
