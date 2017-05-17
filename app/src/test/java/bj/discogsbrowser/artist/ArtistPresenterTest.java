package bj.discogsbrowser.artist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import bj.discogsbrowser.artistreleases.ArtistReleasesFactory;
import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.rxmodifiers.RemoveUnwantedLinksFunction;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Josh Laird on 19/04/2017.
 */
public class ArtistPresenterTest
{
    @Mock Context context;
    @Mock ArtistContract.View view;
    @Mock DiscogsInteractor discogsInteractor;
    private TestScheduler testScheduler;
    @Mock LogWrapper logWrapper;
    @Mock ArtistController artistController;
    @Mock RecyclerView recyclerView;
    @Mock Toolbar toolbar;
    @Mock RemoveUnwantedLinksFunction unwantedLinksFunction;

    private ArtistPresenter artistPresenter;
    private String myId = "12345";

    @Before
    public void setUp() throws Exception
    {
        initMocks(this);
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        artistPresenter = new ArtistPresenter(view, discogsInteractor, testSchedulerProvider, logWrapper, artistController, unwantedLinksFunction);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, logWrapper, artistController, unwantedLinksFunction);
    }

    @Test
    public void getsDataValid_controllerSetsArtist() throws Exception
    {
        ArtistResult testArtistResult = ArtistReleasesFactory.getTestArtistResultNoMembers();
        when(discogsInteractor.fetchArtistDetails(myId)).thenReturn(Single.just(testArtistResult));
        when(unwantedLinksFunction.apply(testArtistResult)).thenReturn(testArtistResult);

        artistPresenter.fetchReleaseDetails(myId);
        testScheduler.triggerActions();

        verify(unwantedLinksFunction).apply(testArtistResult);
        verify(discogsInteractor).fetchArtistDetails(myId);
        verify(artistController).setLoading(true);
        verify(artistController).setArtist(testArtistResult);
        verify(logWrapper).e(any(String.class), any(String.class));
    }

    @Test
    public void getsDataError_setsControllerError() throws Exception
    {
        when(discogsInteractor.fetchArtistDetails(myId)).thenReturn(Single.error(new Exception()));

        artistPresenter.fetchReleaseDetails(myId);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchArtistDetails(myId);
        verify(artistController).setLoading(true);
        verify(artistController).setError(true);
        verify(logWrapper).e(any(String.class), any(String.class));
    }
//
//    @Test
//    public void setupRecyclerView_setsUpRecyclerView()
//    {
//        artistPresenter.setupRecyclerView(context, recyclerView, "title");
//
//        verify(recyclerView).setAdapter(any());
//        verify(artistController).setTitle("title");
//        verify(artistController).getAdapter();
//        verify(artistController).requestModelBuild();
//    }
}