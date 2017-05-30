package bj.vinylbrowser.artistreleases.child;

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
public class ArtistReleasesChildModule
{
    private ArtistReleasesChildContract.View view;

    public ArtistReleasesChildModule(ArtistReleasesChildContract.View view)
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
    protected ArtistReleasesChildContract.View providesView()
    {
        return view;
    }

    @Provides
    protected ArtistReleasesChildPresenter providePresenter(ArtistResultFunction artistResultFunction,
                                                            ArtistReleaseBehaviorRelay relay, MySchedulerProvider mySchedulerProvider,
                                                            ArtistReleasesTransformer transformer)
    {
        return new ArtistReleasesChildPresenter(new CompositeDisposable(), artistResultFunction, relay, mySchedulerProvider, transformer);
    }
}
