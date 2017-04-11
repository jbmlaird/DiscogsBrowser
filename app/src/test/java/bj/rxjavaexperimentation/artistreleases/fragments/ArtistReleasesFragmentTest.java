package bj.rxjavaexperimentation.artistreleases.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jakewharton.rxrelay2.BehaviorRelay;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.model.artistrelease.ArtistRelease;
import bj.rxjavaexperimentation.schedulerprovider.TestSchedulerProvider;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 11/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArtistReleasesFragmentTest
{
    private ArtistReleasesFragment artistReleasesFragment;
    private TestScheduler testScheduler;

    @Mock FragmentActivity fragmentActivity;
    @Mock Bundle bundle;
    BehaviorRelay<List<ArtistRelease>> behaviorRelay;
    @Mock Consumer consumer;
    @Mock ArtistResultFunction artistResultFunction;
    @Mock RecyclerViewReleasesAdapter rvRemixesAdapter;

    @Before
    public void setup()
    {
        testScheduler = new TestScheduler();
        artistReleasesFragment = new ArtistReleasesFragment();
        artistReleasesFragment.setArguments(bundle);
    }

    @Test
    public void connectToBehaviorRelay()
    {
        List<ArtistRelease> list = new ArrayList<>();
        list.add(new ArtistRelease());
        behaviorRelay = BehaviorRelay.createDefault(list);
        artistReleasesFragment.setArtistResultFunction(artistResultFunction);
        when(bundle.getString("map")).thenReturn("Main");
        when(artistResultFunction.map(any(String.class))).thenReturn(artistReleases -> artistReleases);
        artistReleasesFragment.connectToBehaviorRelay(behaviorRelay, artistResultFunction, consumer, new TestSchedulerProvider(testScheduler));
    }
}