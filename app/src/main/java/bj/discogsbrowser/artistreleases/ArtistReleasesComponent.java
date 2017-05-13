package bj.discogsbrowser.artistreleases;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.AppComponent;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentComponent;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.rxmodifiers.ArtistReleasesTransformer;
import dagger.Component;

/**
 * Created by Josh Laird on 10/04/2017.
 */
@ActivityScope
@Component(modules = {ArtistReleasesModule.class}, dependencies = {AppComponent.class})
public interface ArtistReleasesComponent
{
    void inject(ArtistReleasesActivity artistReleasesActivity);

    ArtistReleasesFragmentComponent.Builder artistReleasesFragmentComponentBuilder();

    BehaviorRelay<List<ArtistRelease>> provideRelay();

    ArtistReleasesTransformer provideTransformer();
}
