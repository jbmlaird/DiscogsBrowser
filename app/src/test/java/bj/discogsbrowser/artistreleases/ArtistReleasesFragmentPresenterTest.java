package bj.discogsbrowser.artistreleases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragment;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentContract;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentPresenter;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.utils.rxmodifiers.ArtistResultFunction;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArtistReleasesFragmentPresenterTest
{
    private ArtistReleasesFragmentPresenter presenter;
    @Mock ArtistReleasesFragmentContract.View view;
    @Mock CompositeDisposable disposable;
    @Mock ArtistResultFunction artistResultFunction;
    private ArtistReleaseBehaviorRelay behaviorRelay = new ArtistReleaseBehaviorRelay();
    private TestScheduler testScheduler = new TestScheduler();
    @Mock ArtistReleasesTransformer artistReleasesTransformer;
    @Mock ArtistReleasesController controller;

    @Before
    public void setup()
    {
        presenter = new ArtistReleasesFragmentPresenter(disposable, artistResultFunction, behaviorRelay,
                new TestSchedulerProvider(testScheduler), artistReleasesTransformer);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, disposable, artistResultFunction,
                artistReleasesTransformer, controller);
    }

    @Test
    public void unsubscribeDispose_unsubsDisposes()
    {
        presenter.unsubscribe();
        presenter.dispose();

        verify(disposable, times(1)).clear();
        verify(disposable, times(1)).dispose();
    }

    @Test
    public void behaviorRelayValid_controllerDisplays() throws Exception
    {
        ArtistReleasesFragment mockArtistReleasesFragment = mock(ArtistReleasesFragment.class);
        when(mockArtistReleasesFragment.getController()).thenReturn(controller);
        ArrayList<ArtistRelease> artistReleases = new ArrayList<>();
        artistReleases.add(new ArtistRelease());
        Single<List<ArtistRelease>> just = Single.just(artistReleases);

        presenter.bind(mockArtistReleasesFragment);
        when(disposable.add(any(Disposable.class))).thenReturn(true);
        when(artistResultFunction.apply(artistReleases)).thenReturn(artistReleases);
        when(artistReleasesTransformer.apply(any(Single.class))).thenReturn(just);

        presenter.connectToBehaviorRelay("filter");
        behaviorRelay.getArtistReleaseBehaviorRelay().accept(artistReleases);
        testScheduler.triggerActions();

        verify(disposable, times(1)).add(any(Disposable.class));
        verify(artistResultFunction, times(1)).setParameterToMapTo("filter");
        verify(artistResultFunction, times(1)).apply(artistReleases);
        verify(artistReleasesTransformer, times(1)).apply(any(Single.class));
        verify(controller, times(1)).setItems(artistReleases);
    }

    @Test
    public void behaviorRelayError_controllerSetsError() throws Exception
    {
        ArtistReleasesFragment mockArtistReleasesFragment = mock(ArtistReleasesFragment.class);
        when(mockArtistReleasesFragment.getController()).thenReturn(controller);
        when(disposable.add(any())).thenReturn(true);
        ArrayList<ArtistRelease> artistReleases = new ArrayList<>();
        artistReleases.add(new ArtistRelease());
        Single<Object> error = Single.error(new Throwable());
        when(disposable.add(any(Disposable.class))).thenReturn(true);
        when(artistResultFunction.apply(artistReleases)).thenReturn(artistReleases);
        when(artistReleasesTransformer.apply(any(Single.class))).thenReturn(error);

        presenter.bind(mockArtistReleasesFragment);
        presenter.connectToBehaviorRelay("filter");
        behaviorRelay.getArtistReleaseBehaviorRelay().accept(artistReleases);
        testScheduler.triggerActions();

        verify(disposable, times(1)).add(any(Disposable.class));
        verify(artistResultFunction, times(1)).setParameterToMapTo("filter");
        verify(artistResultFunction, times(1)).apply(artistReleases);
        verify(artistReleasesTransformer, times(1)).apply(any(Single.class));
        verify(controller, times(1)).setError(any(String.class));
    }
}
