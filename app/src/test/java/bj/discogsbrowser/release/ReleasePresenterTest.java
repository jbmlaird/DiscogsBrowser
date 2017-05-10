package bj.discogsbrowser.release;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyControllerAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bj.discogsbrowser.greendao.DaoInteractor;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.order.OrderFactory;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
    @Mock ReleaseContract.View view;
    @Mock ReleaseController controller;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock LabelInteractor labelInteractor;
    @Mock CollectionWantlistInteractor collectionWantlistInteractor;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock SharedPrefsManager sharedPrefsManager;
    @Mock LogWrapper logWrapper;
    @Mock DaoInteractor daoInteractor;
    @Mock ArtistsBeautifier artistsBeautifier;
    private OrderFactory orderFactory = new OrderFactory();

    @Before
    public void setUp()
    {
        presenter = new ReleasePresenter(controller, discogsInteractor, labelInteractor, collectionWantlistInteractor,
                new TestSchedulerProvider(testScheduler), sharedPrefsManager, logWrapper, daoInteractor, artistsBeautifier);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, controller, discogsInteractor,
                sharedPrefsManager, logWrapper, daoInteractor, artistsBeautifier);
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


}
