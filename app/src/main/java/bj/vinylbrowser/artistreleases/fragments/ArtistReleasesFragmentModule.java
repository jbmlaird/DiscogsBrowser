package bj.vinylbrowser.artistreleases.fragments;

import bj.vinylbrowser.artistreleases.ArtistReleaseBehaviorRelay;
import bj.vinylbrowser.di.scopes.FragmentScope;
import bj.vinylbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.vinylbrowser.utils.rxmodifiers.ArtistResultFunction;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
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
    protected ArtistResultFunction providesFunction()
    {
        return new ArtistResultFunction();
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
