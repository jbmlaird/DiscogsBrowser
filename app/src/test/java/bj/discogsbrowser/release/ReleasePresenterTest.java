package bj.discogsbrowser.release;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReleasePresenterTest
{
    private ReleasePresenter presenter;
    @Mock ReleaseController controller;
    @Mock DiscogsInteractor discogsInteractor;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock DaoManager daoManager;
    @Mock ArtistsBeautifier artistsBeautifier;
    private ReleaseFactory releaseFactory = new ReleaseFactory();
    private String id = "123";

    @Before
    public void setUp()
    {
        presenter = new ReleasePresenter(controller, discogsInteractor,
                new TestSchedulerProvider(testScheduler), daoManager, artistsBeautifier);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(controller, discogsInteractor,
                daoManager, artistsBeautifier);
    }

//    @Test
//    public void setupRecyclerView_setsUpRecyclerView()
//    {
//        Context mockCtx = mock(Context.class);
//        RecyclerView mockRv = mock(RecyclerView.class);
//        EpoxyControllerAdapter mockAdapter = mock(EpoxyControllerAdapter.class);
//        String title = "yedawg";
//        when(controller.getAdapter()).thenReturn(mockAdapter);
//
//        presenter.setupRecyclerView(mockCtx, mockRv, title);
//
//        verify(mockRv).setLayoutManager(any(LinearLayoutManager.class));
//        verify(mockRv).setAdapter(mockAdapter);
//        verify(controller).getAdapter();
//        verify(controller).setTitle(title);
//        verify(controller).requestModelBuild();
//    }

    @Test
    public void getReleaseAndLabelDetailsError_displaysError()
    {
        when(discogsInteractor.fetchReleaseDetails(id)).thenReturn(Single.error(new Throwable()));

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchReleaseDetails(id);
        verify(controller).setReleaseLoading(true);
        verify(controller).setReleaseError(true);
    }

    @Test
    public void fetchReleaseListingsError_displaysError() throws IOException
    {
        when(discogsInteractor.getReleaseMarketListings(id)).thenReturn(Single.error(new Throwable()));

        presenter.fetchReleaseListings(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).getReleaseMarketListings(id);
        verify(controller).setListingsLoading(true);
        verify(controller).setReleaseListingsError();
    }

    @Test
    public void checkCollectionError_displaysError() throws IOException
    {
        Release releaseNoLabelNoneForSale = releaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos();
        when(controller.getRelease()).thenReturn(releaseNoLabelNoneForSale);
        when(discogsInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.error(new Throwable()));

        presenter.checkCollectionWantlist();
        testScheduler.triggerActions();

        verify(controller, times(1)).getRelease();
        verify(discogsInteractor).checkIfInCollection(controller, releaseNoLabelNoneForSale);
        verify(controller).setCollectionWantlistError(true);
    }

    @Test
    public void checkCollectionValid_displaysValid() throws IOException
    {
        Release releaseNoLabelNoneForSale = releaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos();
        when(controller.getRelease()).thenReturn(releaseNoLabelNoneForSale);
        when(discogsInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(Collections.emptyList()));
        when(discogsInteractor.checkIfInWantlist(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(Collections.emptyList()));

        presenter.checkCollectionWantlist();
        testScheduler.triggerActions();

        verify(controller, times(2)).getRelease();
        verify(discogsInteractor).checkIfInCollection(controller, releaseNoLabelNoneForSale);
        verify(discogsInteractor).checkIfInWantlist(controller, releaseNoLabelNoneForSale);
        verify(controller).setCollectionWantlistChecked(true);
    }

    @Test
    public void getReleaseNoLabelNoneForSale_displaysRelease()
    {
        Release releaseNoLabelNoneForSale = releaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos();
        Single<Release> releaseSingle = Single.just(releaseNoLabelNoneForSale);
        when(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle);
        ArgumentCaptor<ArrayList> arrayListArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        when(discogsInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(discogsInteractor.checkIfInWantlist(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(controller.getRelease()).thenReturn(releaseNoLabelNoneForSale);

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchReleaseDetails(id);
        verify(controller).setReleaseLoading(true);
        verify(daoManager).storeViewedRelease(releaseNoLabelNoneForSale, artistsBeautifier);
        verify(controller).setRelease(releaseNoLabelNoneForSale);
        verify(controller).setReleaseListings(arrayListArgumentCaptor.capture());
        assertEquals(arrayListArgumentCaptor.getValue().size(), 0);
        verify(discogsInteractor, times(1)).checkIfInCollection(controller, releaseNoLabelNoneForSale);
        verify(discogsInteractor, times(1)).checkIfInWantlist(controller, releaseNoLabelNoneForSale);
        verify(controller, times(2)).getRelease();
        verify(controller).setCollectionWantlistChecked(true);
    }

    @Test
    public void getReleaseWithLabelNoneForSale_displaysRelease()
    {
        Release releaseWithLabelNoneForSale = releaseFactory.getReleaseWithLabelNoneForSale();
        Single<Release> releaseSingle = Single.just(releaseWithLabelNoneForSale);
        when(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle);
        when(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(releaseFactory.getLabelDetails()));
        ArgumentCaptor<ArrayList> arrayListArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        when(discogsInteractor.checkIfInCollection(controller, releaseWithLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(discogsInteractor.checkIfInWantlist(controller, releaseWithLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(controller.getRelease()).thenReturn(releaseWithLabelNoneForSale);

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchReleaseDetails(id);
        verify(controller).setReleaseLoading(true);
        verify(discogsInteractor).fetchLabelDetails(id);
        verify(daoManager).storeViewedRelease(releaseWithLabelNoneForSale, artistsBeautifier);
        verify(controller).setRelease(releaseWithLabelNoneForSale);
        verify(controller).setReleaseListings(arrayListArgumentCaptor.capture());
        assertEquals(arrayListArgumentCaptor.getValue().size(), 0);
        verify(discogsInteractor, times(1)).checkIfInCollection(controller, releaseWithLabelNoneForSale);
        verify(discogsInteractor, times(1)).checkIfInWantlist(controller, releaseWithLabelNoneForSale);
        verify(controller, times(2)).getRelease();
        verify(controller).setCollectionWantlistChecked(true);
    }

    @Test
    public void getReleaseWithLabelAndListings_displaysReleaseAndListings() throws IOException
    {
        Release releaseWithLabelSomeForSale = releaseFactory.getReleaseWithLabelSomeForSale();
        Single<Release> releaseSingle = Single.just(releaseWithLabelSomeForSale);
        when(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle);
        when(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(releaseFactory.getLabelDetails()));
        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        when(discogsInteractor.getReleaseMarketListings(id)).thenReturn(Single.just(releaseFactory.getFourScrapeListings()));

        when(discogsInteractor.checkIfInCollection(controller, releaseWithLabelSomeForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(discogsInteractor.checkIfInWantlist(controller, releaseWithLabelSomeForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(controller.getRelease()).thenReturn(releaseWithLabelSomeForSale);

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchReleaseDetails(id);
        verify(controller).setReleaseLoading(true);
        verify(discogsInteractor).fetchLabelDetails(id);
        verify(daoManager).storeViewedRelease(releaseWithLabelSomeForSale, artistsBeautifier);
        verify(controller).setRelease(releaseWithLabelSomeForSale);
        verify(controller).setReleaseListings(listArgumentCaptor.capture());
        assertEquals(listArgumentCaptor.getValue().size(), 4);
        verify(discogsInteractor, times(1)).checkIfInCollection(controller, releaseWithLabelSomeForSale);
        verify(discogsInteractor, times(1)).checkIfInWantlist(controller, releaseWithLabelSomeForSale);
        verify(discogsInteractor).getReleaseMarketListings(id);
        verify(controller, times(2)).getRelease();
        verify(controller).setCollectionWantlistChecked(true);
        verify(controller).setListingsLoading(true);
    }
}
