package bj.discogsbrowser.release;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyControllerAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.model.release.Release;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
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
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReleasePresenterTest
{
    private ReleasePresenter presenter;
    @Mock ReleaseController controller;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock LabelInteractor labelInteractor;
    @Mock CollectionWantlistInteractor collectionWantlistInteractor;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock DaoManager daoManager;
    @Mock ArtistsBeautifier artistsBeautifier;
    private ReleaseFactory releaseFactory = new ReleaseFactory();
    private String id = "123";

    @Before
    public void setUp()
    {
        presenter = new ReleasePresenter(controller, discogsInteractor, labelInteractor, collectionWantlistInteractor,
                new TestSchedulerProvider(testScheduler), daoManager, artistsBeautifier);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(controller, discogsInteractor, labelInteractor, collectionWantlistInteractor,
                daoManager, artistsBeautifier);
    }

    @Test
    public void setupRecyclerView_setsUpRecyclerView()
    {
        Context mockCtx = mock(Context.class);
        RecyclerView mockRv = mock(RecyclerView.class);
        EpoxyControllerAdapter mockAdapter = mock(EpoxyControllerAdapter.class);
        String title = "yedawg";
        when(controller.getAdapter()).thenReturn(mockAdapter);

        presenter.setupRecyclerView(mockCtx, mockRv, title);

        verify(mockRv).setLayoutManager(any(LinearLayoutManager.class));
        verify(mockRv).setAdapter(mockAdapter);
        verify(controller).getAdapter();
        verify(controller).setTitle(title);
        verify(controller).requestModelBuild();
    }

    @Test
    public void getReleaseNoLabelNoneForSale_displaysRelease()
    {
        Release releaseNoLabelNoneForSale = releaseFactory.getReleaseNoLabelNoneForSale();
        Single<Release> releaseSingle = Single.just(releaseNoLabelNoneForSale);
        when(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle);
        ArgumentCaptor<ArrayList> arrayListArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        when(collectionWantlistInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(collectionWantlistInteractor.checkIfInWantlist(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(controller.getRelease()).thenReturn(releaseNoLabelNoneForSale);

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchReleaseDetails(id);
        verify(controller).setReleaseLoading(true);
        verify(daoManager).storeViewedRelease(releaseNoLabelNoneForSale, artistsBeautifier);
        verify(controller).setRelease(releaseNoLabelNoneForSale);
        verify(controller).setReleaseListings(arrayListArgumentCaptor.capture());
        assertEquals(arrayListArgumentCaptor.getValue().size(), 0);
        verify(collectionWantlistInteractor, times(1)).checkIfInCollection(controller, releaseNoLabelNoneForSale);
        verify(collectionWantlistInteractor, times(1)).checkIfInWantlist(controller, releaseNoLabelNoneForSale);
        verify(controller, times(2)).getRelease();
        verify(controller).collectionWantlistChecked(true);
    }

    @Test
    public void getReleaseWithLabelNoneForSale_displaysRelease()
    {
        Release releaseNoLabelNoneForSale = releaseFactory.getReleaseNoLabelNoneForSale();
        Single<Release> releaseSingle = Single.just(releaseNoLabelNoneForSale);
        when(discogsInteractor.fetchReleaseDetails(id)).thenReturn(releaseSingle);
        ArgumentCaptor<ArrayList> arrayListArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        when(collectionWantlistInteractor.checkIfInCollection(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(collectionWantlistInteractor.checkIfInWantlist(controller, releaseNoLabelNoneForSale)).thenReturn(Single.just(new ArrayList<>()));
        when(controller.getRelease()).thenReturn(releaseNoLabelNoneForSale);

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchReleaseDetails(id);
        verify(controller).setReleaseLoading(true);
        verify(daoManager).storeViewedRelease(releaseNoLabelNoneForSale, artistsBeautifier);
        verify(controller).setRelease(releaseNoLabelNoneForSale);
        verify(controller).setReleaseListings(arrayListArgumentCaptor.capture());
        assertEquals(arrayListArgumentCaptor.getValue().size(), 0);
        verify(collectionWantlistInteractor, times(1)).checkIfInCollection(controller, releaseNoLabelNoneForSale);
        verify(collectionWantlistInteractor, times(1)).checkIfInWantlist(controller, releaseNoLabelNoneForSale);
        verify(controller, times(2)).getRelease();
        verify(controller).collectionWantlistChecked(true);
    }
}
