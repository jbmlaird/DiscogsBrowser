package bj.discogsbrowser.artistreleases.fragments;

import bj.discogsbrowser.artistreleases.ArtistReleaseBehaviorRelay;
import bj.discogsbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.utils.rxmodifiers.ArtistResultFunction;
import bj.discogsbrowser.di.scopes.FragmentScope;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@Module
public class ArtistReleasesFragmentModule
{
    private ArtistReleasesFragmentContract.View view;

    public ArtistReleasesFragmentModule(ArtistReleasesFragmentContract.View view)
    {
        this.view = view;
    }

    @Provides
    @FragmentScope
    protected ArtistReleasesFragmentContract.View providesView()
    {
        return view;
    }

    @Provides
    protected ArtistReleasesFragmentPresenter providePresenter(ArtistResultFunction artistResultFunction,
                                                               ArtistReleaseBehaviorRelay relay, MySchedulerProvider mySchedulerProvider,
                                                               ArtistReleasesTransformer transformer)
    {
        return new ArtistReleasesFragmentPresenter(new CompositeDisposable(), artistResultFunction, relay, mySchedulerProvider, transformer);
    }
}
