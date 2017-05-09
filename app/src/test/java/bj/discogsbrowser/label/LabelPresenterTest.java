package bj.discogsbrowser.label;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.LabelRelease;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LabelPresenterTest
{
    @Mock LabelController controller;
    @Mock DiscogsInteractor discogsInteractor;
    private TestScheduler testScheduler = new TestScheduler();
    private LabelPresenter presenter;
    private String id = "123";

    @Before
    public void setUp() throws Exception
    {
        presenter = new LabelPresenter(controller, discogsInteractor, new TestSchedulerProvider(testScheduler));
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(controller, discogsInteractor);
    }

    @Test
    public void setupRecyclerView_setsUpRecyclerView()
    {
        String title = "title";
        EpoxyControllerAdapter mockAdapter = mock(EpoxyControllerAdapter.class);
        Context mockCtx = mock(Context.class);
        RecyclerView mockRv = mock(RecyclerView.class);
        when(controller.getAdapter()).thenReturn(mockAdapter);

        presenter.setupRecyclerView(mockCtx, mockRv, title);

        verify(mockRv, times(1)).setLayoutManager(any(LinearLayoutManager.class));
        verify(mockRv, times(1)).setAdapter(mockAdapter);
        verify(controller, times(1)).getAdapter();
        verify(controller, times(1)).setTitle(title);
        verify(controller, times(1)).requestModelBuild();
    }

    @Test
    public void fetchLabelDetailsNoReleasesSuccess_controllerDisplays()
    {
        Label label = new Label();
        List<LabelRelease> emptyList = Collections.emptyList();
        when(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(label));
        when(discogsInteractor.fetchLabelReleases(id)).thenReturn(Single.just(emptyList));

        presenter.getData(id);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchLabelDetails(id);
        verify(controller, times(1)).setLoading(true);
        verify(controller).setLabel(label);
        verify(discogsInteractor, times(1)).fetchLabelReleases(id);
        verify(controller, times(1)).setLabelReleases(emptyList);
    }

    @Test
    public void fetchLabelDetailsReleasesSuccess_controllerDisplays()
    {
        Label label = new Label();
        List<LabelRelease> labelReleases = new ArrayList<>();
        labelReleases.add(new LabelRelease());
        labelReleases.add(new LabelRelease());
        when(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(label));
        when(discogsInteractor.fetchLabelReleases(id)).thenReturn(Single.just(labelReleases));

        presenter.getData(id);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchLabelDetails(id);
        verify(controller, times(1)).setLoading(true);
        verify(controller).setLabel(label);
        verify(discogsInteractor, times(1)).fetchLabelReleases(id);
        verify(controller, times(1)).setLabelReleases(labelReleases);
    }

    @Test
    public void fetchLabelDetailsError_controllerError()
    {
        when(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.error(new Throwable()));

        presenter.getData(id);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchLabelDetails(id);
        verify(controller, times(1)).setLoading(true);
        verify(controller).setError(true);
    }
}
