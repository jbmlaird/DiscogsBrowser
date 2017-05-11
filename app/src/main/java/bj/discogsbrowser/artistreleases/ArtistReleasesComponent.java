package bj.discogsbrowser.artistreleases;

import android.content.Context;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Singleton;

import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentComponent;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentModule;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@Singleton
@Component(modules = {ArtistReleasesModule.class}, dependencies = {AppComponent.class})
public interface ArtistReleasesComponent
{
    void inject(ArtistReleasesActivity artistReleasesActivity);

    ArtistReleasesFragmentComponent plus(ArtistReleasesFragmentModule module);

    Context getContext();

    CompositeDisposable getCompositeDisposable();

    BehaviorRelay<List<ArtistRelease>> getBehaviorRelay();
}
