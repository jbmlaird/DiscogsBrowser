package bj.discogsbrowser.artistreleases;

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
import bj.discogsbrowser.utils.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import edu.emory.mathcs.backport.java.util.Collections;
import io.reactivex.Observable;
import io.reactivex.Single;
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
public class ArtistReleasesPresenterTest
{
    private String id = "123";
    private ArtistReleasesPresenter presenter;
    @Mock ArtistReleasesContract.View view;
    @Mock ArtistReleasesController controller;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock ArtistReleaseBehaviorRelay artistReleaseBehaviorRelay; // = BehaviorRelay.create();
    private TestScheduler testScheduler;
    @Mock ArtistReleasesTransformer artistReleasesTransformer;
    @Mock BehaviorRelay<List<ArtistRelease>> artistReleases;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        when(artistReleaseBehaviorRelay.getArtistReleaseBehaviorRelay()).thenReturn(BehaviorRelay.create());
        presenter = new ArtistReleasesPresenter(view, discogsInteractor, controller, artistReleaseBehaviorRelay,
                new TestSchedulerProvider(testScheduler), artistReleasesTransformer);
        verify(artistReleaseBehaviorRelay).getArtistReleaseBehaviorRelay();
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, controller, artistReleaseBehaviorRelay, artistReleasesTransformer);
    }

    @Test
    public void getArtistReleasesError_controllerError()
    {
        Single<List<ArtistRelease>> error = Single.error(new Throwable());
        when(discogsInteractor.fetchArtistsReleases(id)).thenReturn(error);

        presenter.fetchArtistReleases(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchArtistsReleases(id);
        verify(controller).setError("Unable to fetch Artist Releases");
    }

    @Test
    public void getArtistReleases_successful() throws Exception
    {
        ArrayList<ArtistRelease> artistReleases = new ArrayList<>();
        artistReleases.add(new ArtistRelease());
        Single<List<ArtistRelease>> just = Single.just(artistReleases);
        when(discogsInteractor.fetchArtistsReleases(id)).thenReturn(just);

        presenter.fetchArtistReleases(id);
        testScheduler.triggerActions();

        verify(artistReleaseBehaviorRelay).getArtistReleaseBehaviorRelay();
        verify(discogsInteractor).fetchArtistsReleases(id);
    }

    @Test
    public void getArtistReleasesError_controllerDisplaysError() throws Exception
    {
        Single<List<ArtistRelease>> error = Single.error(new Throwable());
        when(discogsInteractor.fetchArtistsReleases(id)).thenReturn(error);

        presenter.fetchArtistReleases(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchArtistsReleases(id);
        verify(controller, times(1)).setError(any(String.class));
    }

    @Test
    public void setupFilterRelayEmpty_setsFilterText()
    {
        // mock relay
        BehaviorRelay<List<ArtistRelease>> mockRelay = mock(BehaviorRelay.class);
        presenter.setBehaviorRelay(mockRelay);
        String filterText = "filterText";
        Observable<CharSequence> just = Observable.just(filterText);
        when(view.filterIntent()).thenReturn(just);
        when(mockRelay.getValue()).thenReturn(Collections.emptyList());

        presenter.setupFilter();

        assertEquals(view.filterIntent(), just);
        verify(view, times(2)).filterIntent();
        verify(artistReleasesTransformer, times(1)).setFilterText(filterText);
        verify(mockRelay, times(2)).getValue();
        verifyNoMoreInteractions(mockRelay);
    }

    @Test
    public void setupFilterRelayItem_setsFilterTextEmits()
    {
        // Mock Relay
        BehaviorRelay<List<ArtistRelease>> mockRelay = mock(BehaviorRelay.class);
        presenter.setBehaviorRelay(mockRelay);
        String filterText = "filterText";
        ArtistRelease artistRelease = new ArtistRelease();
        List list = Collections.singletonList(artistRelease);
        Observable<CharSequence> just = Observable.just(filterText);
        when(view.filterIntent()).thenReturn(just);
        when(mockRelay.getValue()).thenReturn(list);

        presenter.setupFilter();

        assertEquals(view.filterIntent(), just);
        verify(view, times(2)).filterIntent();
        verify(artistReleasesTransformer, times(1)).setFilterText(filterText);
        verify(mockRelay, times(3)).getValue();
        verify(mockRelay, times(1)).accept(list);
    }
}
