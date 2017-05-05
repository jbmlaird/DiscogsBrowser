package bj.rxjavaexperimentation.artist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.testmodels.TestArtistResult;
import bj.rxjavaexperimentation.utils.schedulerprovider.TestSchedulerProvider;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.Single;
import io.reactivex.functions.Function;
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
    private String release = "release";
    private String artist = "artist";
    private String master = "master";
    private String label = "label";

    @Mock Context context;
    @Mock ArtistContract.View view;
    @Mock DiscogsInteractor discogsInteractor;
    private TestScheduler testScheduler;
    @Mock LogWrapper logWrapper;
    @Mock ArtistController artistController;
    @Mock RecyclerView recyclerView;
    @Mock Toolbar toolbar;
    @Mock Function<ArtistResult, ArtistResult> artistResultFunction;

    private ArtistPresenter artistPresenter;
    private String myId = "12345";

    @Before
    public void setUp() throws Exception
    {
        initMocks(this);
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        artistPresenter = new ArtistPresenter(view, discogsInteractor, testSchedulerProvider, logWrapper, artistController, artistResultFunction);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, logWrapper, artistController, artistResultFunction);
    }

    @Test
    public void getsDataValid_controllerSetsArtist() throws Exception
    {
        TestArtistResult testArtistResult = new TestArtistResult();
        when(discogsInteractor.fetchArtistDetails(myId)).thenReturn(Single.just(testArtistResult));
        when(artistResultFunction.apply(testArtistResult)).thenReturn(testArtistResult);

        artistPresenter.getData(myId);
        testScheduler.triggerActions();

        verify(artistResultFunction).apply(testArtistResult);
        verify(discogsInteractor).fetchArtistDetails(myId);
        verify(artistController).setLoading(true);
        verify(artistController).setArtist(testArtistResult);
        verify(logWrapper).e(any(String.class), any(String.class));
    }

    @Test
    public void getsDataError_setsControllerError() throws Exception
    {
        when(discogsInteractor.fetchArtistDetails(myId)).thenReturn(Single.error(new Exception()));

        artistPresenter.getData(myId);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchArtistDetails(myId);
        verify(artistController).setLoading(true);
        verify(artistController).setError(true);
        verify(logWrapper).e(any(String.class), any(String.class));
    }

    @Test
    public void setupRecyclerView_setsUpRecyclerView()
    {
        artistPresenter.setupRecyclerView(context, recyclerView, "title");

        verify(recyclerView).setAdapter(any());
        verify(artistController).setTitle("title");
        verify(artistController).getAdapter();
        verify(artistController).requestModelBuild();
    }
}