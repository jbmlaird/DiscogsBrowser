package bj.rxjavaexperimentation.detailedview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import bj.rxjavaexperimentation.detailedview.epoxy.DetailedAdapter;
import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.TestSchedulerProvider;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Josh Laird on 19/04/2017.
 */
public class DetailedPresenterTest
{
    private String release = "release";
    private String artist = "artist";
    private String master = "master";
    private String label = "label";

    @Mock Context context;
    @Mock DetailedContract.View view;
    @Mock SearchDiscogsInteractor searchDiscogsInteractor;
    @Mock DetailedBodyModelPresenter detailedBodyModelPresenter;
    private TestScheduler testScheduler;
    @Mock ArtistsBeautifier artistsBeautifier;
    @Mock DetailedAdapter detailedAdapter;
    @Mock CompositeDisposable compositeDisposable;
    @Mock LogWrapper logWrapper;

    @Mock RecyclerView recyclerView;
    @Mock Toolbar toolbar;

    private DetailedPresenter detailedPresenter;
    private DetailedMockObjects detailedMockObjects;

    @Before
    public void setUp() throws Exception
    {
        initMocks(this);
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        detailedPresenter = new DetailedPresenter(context, view, searchDiscogsInteractor, detailedBodyModelPresenter, testSchedulerProvider, artistsBeautifier, detailedAdapter, compositeDisposable, logWrapper);

        detailedMockObjects = new DetailedMockObjects();
    }

    @Test
    public void fetchValidRelease_updatesAdapter() throws Exception
    {
        ArrayList emptyArrayList = new ArrayList();

        when(searchDiscogsInteractor.fetchReleaseDetails("0")).thenReturn(Observable.just(detailedMockObjects.getMockRelease()));
        when(searchDiscogsInteractor.getReleaseMarketListings("0", release)).thenReturn(Observable.just(emptyArrayList));

        detailedPresenter.fetchDetailedInformation(release, "0");
        testScheduler.triggerActions();

        verify(logWrapper).e(detailedPresenter.getClass().getSimpleName(), detailedMockObjects.getMockRelease().getTitle());
        verify(searchDiscogsInteractor).fetchReleaseDetails("0");
        verify(searchDiscogsInteractor).getReleaseMarketListings("0", release);
        verify(detailedAdapter).addRelease(detailedMockObjects.getMockRelease());
        verify(detailedAdapter).setReleaseListings(emptyArrayList);
    }

    @Test
    public void fetchValidRelease_throwsMarketException() throws Exception
    {
        when(searchDiscogsInteractor.fetchReleaseDetails("0")).thenReturn(Observable.just(detailedMockObjects.getMockRelease()));
        when(searchDiscogsInteractor.getReleaseMarketListings("0", release)).thenReturn(Observable.error(Throwable::new));

        detailedPresenter.fetchDetailedInformation(release, "0");
        testScheduler.triggerActions();

        verify(logWrapper).e(detailedPresenter.getClass().getSimpleName(), detailedMockObjects.getMockRelease().getTitle());
        verify(searchDiscogsInteractor).fetchReleaseDetails("0");
        verify(searchDiscogsInteractor).getReleaseMarketListings("0", release);
        verify(detailedAdapter).addRelease(detailedMockObjects.getMockRelease());
        verify(detailedAdapter).setReleaseListingsError();
    }

    @Test
    public void fetchInvalidRelease_throwsException() throws Exception
    {
        when(searchDiscogsInteractor.fetchReleaseDetails("0")).thenReturn(Observable.error(Throwable::new));

        detailedPresenter.fetchDetailedInformation(release, "0");
        testScheduler.triggerActions();

        verify(searchDiscogsInteractor).fetchReleaseDetails("0");
        verify(logWrapper).e(detailedPresenter.getClass().getSimpleName(), "onReleaseDetailsError");
    }

    @Test
    public void setupRecyclerView_setsUp() throws Exception
    {
        detailedPresenter.setupRecyclerView(recyclerView, "title", toolbar);

        verify(recyclerView).setLayoutManager(any(LinearLayoutManager.class));
        verify(detailedAdapter).setHeader("title");
        verify(recyclerView).setAdapter(detailedAdapter);
    }

    @Test
    public void displayLabelReleases_viewDisplaysReleases() throws Exception
    {
        detailedPresenter.displayLabelReleases("0", "url");
        verify(view).displayLabelReleases("0", "url");
    }

    @Test
    public void unsubscribe_disposes() throws Exception
    {
        detailedPresenter.unsubscribe();
        verify(compositeDisposable).dispose();
    }
}