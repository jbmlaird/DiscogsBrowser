package bj.vinylbrowser.artistreleases

import bj.vinylbrowser.artistreleases.child.ArtistReleasesChildController
import bj.vinylbrowser.artistreleases.child.ArtistReleasesChildContract
import bj.vinylbrowser.artistreleases.child.ArtistReleasesChildPresenter
import bj.vinylbrowser.model.artistrelease.ArtistRelease
import bj.vinylbrowser.utils.rxmodifiers.ArtistReleasesTransformer
import bj.vinylbrowser.utils.rxmodifiers.ArtistResultFunction
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class ArtistReleasesChildPresenterTest {
    lateinit var presenter: ArtistReleasesChildPresenter
    val view: ArtistReleasesChildContract.View = mock()
    val disposable: CompositeDisposable = mock()
    val artistResultFunction: ArtistResultFunction = mock()
    val behaviorRelay = ArtistReleaseBehaviorRelay()
    val testScheduler = TestScheduler()
    val artistReleasesTransformer: ArtistReleasesTransformer = mock()
    val epxController: ArtistReleasesEpxController = mock()

    @Before
    fun setup() {
        presenter = ArtistReleasesChildPresenter(disposable, artistResultFunction, behaviorRelay,
                TestSchedulerProvider(testScheduler), artistReleasesTransformer)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, disposable, artistResultFunction,
                artistReleasesTransformer, epxController)
    }

    @Test
    fun unsubscribeDispose_unsubsDisposes() {
        presenter.unsubscribe()
        presenter.dispose()

        verify(disposable, times(1)).clear()
        verify(disposable, times(1)).dispose()
    }

    @Test
    @Throws(Exception::class)
    fun behaviorRelayValid_controllerDisplays() {
        val mockChildController: ArtistReleasesChildController = mock()
        whenever(mockChildController.controller).thenReturn(epxController)
        val artistReleases = ArrayList<ArtistRelease>()
        artistReleases.add(ArtistRelease())
        val just = Single.just<List<ArtistRelease>>(artistReleases)

        presenter.bind(mockChildController)
        whenever(disposable.add(any(Disposable::class.java))).thenReturn(true)
        whenever(artistResultFunction.apply(artistReleases)).thenReturn(artistReleases)
        whenever(artistReleasesTransformer.apply(any())).thenReturn(just)

        presenter.connectToBehaviorRelay("filter")
        behaviorRelay.artistReleaseBehaviorRelay.accept(artistReleases)
        testScheduler.triggerActions()

        verify(disposable, times(1)).add(any<Disposable>(Disposable::class.java))
        verify(artistResultFunction, times(1)).setParameterToMapTo("filter")
        verify(artistResultFunction, times(1)).apply(artistReleases)
        verify(artistReleasesTransformer, times(1)).apply(any())
        verify(epxController, times(1)).setItems(artistReleases)
    }

    @Test
    @Throws(Exception::class)
    fun behaviorRelayError_controllerSetsError() {
        val mockChildController: ArtistReleasesChildController = mock()
        whenever(mockChildController.controller).thenReturn(epxController)
        whenever(disposable.add(any<Disposable>())).thenReturn(true)
        val artistReleases = mutableListOf<ArtistRelease>()
        artistReleases.add(ArtistRelease())
        val error = Single.error<List<ArtistRelease>>(Throwable())
        whenever(disposable.add(any<Disposable>(Disposable::class.java))).thenReturn(true)
        whenever(artistResultFunction.apply(artistReleases)).thenReturn(artistReleases)
        whenever(artistReleasesTransformer.apply(any())).thenReturn(error)

        presenter.bind(mockChildController)
        presenter.connectToBehaviorRelay("filter")
        behaviorRelay.artistReleaseBehaviorRelay.accept(artistReleases)
        testScheduler.triggerActions()

        verify(disposable, times(1)).add(any<Disposable>(Disposable::class.java))
        verify(artistResultFunction, times(1)).setParameterToMapTo("filter")
        verify(artistResultFunction, times(1)).apply(artistReleases)
        verify(artistReleasesTransformer, times(1)).apply(any())
        verify(epxController, times(1)).setError(any<String>(String::class.java))
    }
}