package bj.discogsbrowser.artistreleases;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxrelay2.BehaviorRelay;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import edu.emory.mathcs.backport.java.util.Collections;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 08/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArtistReleasesPresenterUnitTest
{
    private String id = "123";
    private ArtistReleasesPresenter presenter;
    @Mock ArtistReleasesContract.View view;
    @Mock ArtistReleasesController controller;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock CompositeDisposable disposable;
    @Mock BehaviorRelay<List<ArtistRelease>> behaviorRelay; // = BehaviorRelay.create();
    private TestScheduler testScheduler;
    @Mock ArtistResultFunction artistResultFunction;
    @Mock ArtistReleasesTransformer artistReleasesTransformer;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = new ArtistReleasesPresenter(view, controller, discogsInteractor, disposable, behaviorRelay,
                new TestSchedulerProvider(testScheduler), artistResultFunction, artistReleasesTransformer);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, disposable, behaviorRelay, artistResultFunction, artistReleasesTransformer);
    }

    @Test
    public void setupRecyclerView_setsUpRecyclerView()
    {
        RecyclerView mockRv = mock(RecyclerView.class);
        FragmentActivity activity = mock(FragmentActivity.class);

        presenter.setupRecyclerView(mockRv, activity);

        verify(mockRv, times(1)).setLayoutManager(any(LinearLayoutManager.class));
        verify(controller, times(1)).getAdapter();
        verify(controller, times(1)).requestModelBuild();
    }

    @Test
    public void getArtistReleasesError_controllerError()
    {
        Single<List<ArtistRelease>> error = Single.error(new Throwable());
        when(discogsInteractor.fetchArtistsReleases(id)).thenReturn(error);

        presenter.getArtistReleases(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchArtistsReleases(id);
        verify(controller).setError("Unable to fetch Artist Releases");
    }

    @Test
    public void getArtistReleasesSuccessful_controllerDisplays() throws Exception
    {
        // Will need to use a real behaviorRelay for this test
        BehaviorRelay<List<ArtistRelease>> behaviorRelay = BehaviorRelay.create();
        presenter = new ArtistReleasesPresenter(view, controller, discogsInteractor, disposable, behaviorRelay,
                new TestSchedulerProvider(testScheduler), artistResultFunction, artistReleasesTransformer);

        ArrayList<ArtistRelease> artistReleases = new ArrayList<>();
        artistReleases.add(new ArtistRelease());
        Single<List<ArtistRelease>> just = Single.just(artistReleases);

        when(disposable.add(any(Disposable.class))).thenReturn(true);
        when(artistResultFunction.apply(artistReleases)).thenReturn(artistReleases);
        when(artistReleasesTransformer.apply(any(Single.class))).thenReturn(just);

        presenter.connectToBehaviorRelay("filter");
        behaviorRelay.accept(artistReleases);
        testScheduler.triggerActions();

        verify(disposable, times(1)).add(any(Disposable.class));
        verify(artistResultFunction, times(1)).setParameterToMapTo("filter");
        verify(artistResultFunction, times(1)).apply(artistReleases);
        verify(artistReleasesTransformer, times(1)).apply(any(Single.class));
        verify(controller, times(1)).setItems(artistReleases);
    }

    @Test
    public void getArtistReleasesError_controllerDisplaysError() throws Exception
    {
        // Will need to use a real behaviorRelay for this test
        BehaviorRelay<List<ArtistRelease>> behaviorRelay = BehaviorRelay.create();
        presenter = new ArtistReleasesPresenter(view, controller, discogsInteractor, disposable, behaviorRelay,
                new TestSchedulerProvider(testScheduler), artistResultFunction, artistReleasesTransformer);

        ArrayList<ArtistRelease> artistReleases = new ArrayList<>();
        artistReleases.add(new ArtistRelease());
        Single<Object> error = Single.error(new Throwable());

        when(disposable.add(any(Disposable.class))).thenReturn(true);
        when(artistResultFunction.apply(artistReleases)).thenReturn(artistReleases);
        when(artistReleasesTransformer.apply(any(Single.class))).thenReturn(error);

        presenter.connectToBehaviorRelay("filter");
        behaviorRelay.accept(artistReleases);
        testScheduler.triggerActions();

        verify(disposable, times(1)).add(any(Disposable.class));
        verify(artistResultFunction, times(1)).setParameterToMapTo("filter");
        verify(artistResultFunction, times(1)).apply(artistReleases);
        verify(artistReleasesTransformer, times(1)).apply(any(Single.class));
        verify(controller, times(1)).setError(any(String.class));
    }

    @Test
    public void setupFilterRelayEmpty_setsFilterText()
    {
        String filterText = "filterText";
        Observable<CharSequence> just = Observable.just(filterText);
        when(view.filterIntent()).thenReturn(just);
        when(behaviorRelay.getValue()).thenReturn(Collections.emptyList());

        presenter.setupFilter();

        assertEquals(view.filterIntent(), just);
        verify(view, times(2)).filterIntent();
        verify(artistReleasesTransformer, times(1)).setFilterText(filterText);
        verify(behaviorRelay, times(2)).getValue();
    }

    @Test
    public void setupFilterRelayItem_setsFilterTextEmits()
    {
        String filterText = "filterText";
        ArtistRelease artistRelease = new ArtistRelease();
        List list = Collections.singletonList(artistRelease);
        Observable<CharSequence> just = Observable.just(filterText);
        when(view.filterIntent()).thenReturn(just);
        when(behaviorRelay.getValue()).thenReturn(list);

        presenter.setupFilter();

        assertEquals(view.filterIntent(), just);
        verify(view, times(2)).filterIntent();
        verify(artistReleasesTransformer, times(1)).setFilterText(filterText);
        verify(behaviorRelay, times(3)).getValue();
        verify(behaviorRelay, times(1)).accept(list);
    }

    @Test
    public void unsubscribeDispose_unsubsDisposes()
    {
        presenter.unsubscribe();
        presenter.dispose();

        verify(disposable, times(1)).clear();
        verify(disposable, times(1)).dispose();
    }
}
