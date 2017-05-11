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
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentContract;
import bj.discogsbrowser.artistreleases.fragments.ArtistReleasesFragmentPresenter;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArtistReleasesFragmentPresenterTest
{
    private ArtistReleasesFragmentPresenter presenter;
    @Mock ArtistReleasesFragmentContract.View view;
    @Mock CompositeDisposable disposable;
    @Mock ArtistResultFunction artistResultFunction;
    @Mock BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock ArtistReleasesTransformer artistReleasesTransformer;
    @Mock ArtistReleasesController controller;

    @Before
    public void setup()
    {
        presenter = new ArtistReleasesFragmentPresenter(view, disposable, artistResultFunction, behaviorRelay,
                new TestSchedulerProvider(testScheduler), artistReleasesTransformer, controller);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, disposable, artistResultFunction, behaviorRelay,
                artistReleasesTransformer, controller);
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
    public void unsubscribeDispose_unsubsDisposes()
    {
        presenter.unsubscribe();
        presenter.dispose();

        verify(disposable, times(1)).clear();
        verify(disposable, times(1)).dispose();
    }

//    @Test
//    public void getArtistReleasesSuccessful_controllerDisplays()
//    {
//        // Will need to use a real behaviorRelay for this test
//        BehaviorRelay<List<ArtistRelease>> behaviorRelay = BehaviorRelay.create();
//        presenter = new ArtistReleasesFragmentPresenter(view, disposable, artistResultFunction, behaviorRelay,
//                new TestSchedulerProvider(testScheduler), artistReleasesTransformer, controller);
//    }

}
