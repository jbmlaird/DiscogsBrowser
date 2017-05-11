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
import bj.discogsbrowser.rxmodifiers.ArtistReleasesTransformer;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import edu.emory.mathcs.backport.java.util.Collections;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    @Mock BehaviorRelay<List<ArtistRelease>> behaviorRelay; // = BehaviorRelay.create();
    private TestScheduler testScheduler;
    @Mock ArtistReleasesTransformer artistReleasesTransformer;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = new ArtistReleasesPresenter(view, discogsInteractor, controller, behaviorRelay,
                new TestSchedulerProvider(testScheduler), artistReleasesTransformer);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, controller, behaviorRelay, artistReleasesTransformer);
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
    public void getArtistReleases_successful() throws Exception
    {
        ArrayList<ArtistRelease> artistReleases = new ArrayList<>();
        artistReleases.add(new ArtistRelease());
        Single<List<ArtistRelease>> just = Single.just(artistReleases);
        when(discogsInteractor.fetchArtistsReleases(id)).thenReturn(just);

        presenter.getArtistReleases(id);
        testScheduler.triggerActions();

        verify(behaviorRelay).accept(artistReleases);
        verify(discogsInteractor).fetchArtistsReleases(id);
    }

    @Test
    public void getArtistReleasesError_controllerDisplaysError() throws Exception
    {
        Single<List<ArtistRelease>> error = Single.error(new Throwable());
        when(discogsInteractor.fetchArtistsReleases(id)).thenReturn(error);

        presenter.getArtistReleases(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchArtistsReleases(id);
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
}
