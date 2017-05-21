package bj.discogsbrowser.artistreleases

import bj.discogsbrowser.model.artistrelease.ArtistRelease
import bj.discogsbrowser.network.DiscogsInteractor
import bj.discogsbrowser.utils.rxmodifiers.ArtistReleasesTransformer
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class ArtistReleasesPresenterTest {
    val id = "123"
    lateinit var presenter: ArtistReleasesPresenter
    val view: ArtistReleasesContract.View = mock()
    val controller: ArtistReleasesController = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    lateinit var testScheduler: TestScheduler
    val artistReleasesTransformer: ArtistReleasesTransformer = mock()

    @Before
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        val artistReleaseBehaviorRelay: ArtistReleaseBehaviorRelay = mock {
            on { artistReleaseBehaviorRelay } doReturn BehaviorRelay.create<List<ArtistRelease>>()
        }
        presenter = ArtistReleasesPresenter(view, discogsInteractor, controller, artistReleaseBehaviorRelay,
                TestSchedulerProvider(testScheduler), artistReleasesTransformer)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, discogsInteractor, controller, artistReleasesTransformer)
    }

    @Test
    fun getArtistReleasesError_controllerError() {
        val error = Single.error<List<ArtistRelease>>(Throwable())
        whenever(discogsInteractor.fetchArtistsReleases(id)).thenReturn(error)

        presenter.fetchArtistReleases(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchArtistsReleases(id)
        verify(controller).setError("Unable to fetch Artist Releases")
    }

    @Test
    @Throws(Exception::class)
    fun getArtistReleases_successful() {
        val artistReleases = ArrayList<ArtistRelease>()
        artistReleases.add(ArtistRelease())
        val just = Single.just<List<ArtistRelease>>(artistReleases)
        whenever(discogsInteractor.fetchArtistsReleases(id)).thenReturn(just)

        presenter.fetchArtistReleases(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchArtistsReleases(id)
    }

    @Test
    @Throws(Exception::class)
    fun getArtistReleasesError_controllerDisplaysError() {
        val error = Single.error<List<ArtistRelease>>(Throwable())
        whenever(discogsInteractor.fetchArtistsReleases(id)).thenReturn(error)

        presenter.fetchArtistReleases(id)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchArtistsReleases(id)
        verify(controller, times(1)).setError(any(String::class.java))
    }

    @Test
    fun setupFilterRelayEmpty_setsFilterText() {
        // mock relay
        val mockRelay: BehaviorRelay<MutableList<ArtistRelease>> = mock()
        presenter.setBehaviorRelay(mockRelay)
        val filterText = "filterText"
        val just = Observable.just<CharSequence>(filterText)
        whenever(view.filterIntent()).thenReturn(just)
        whenever(mockRelay.value).thenReturn(mutableListOf())

        presenter.setupFilter()

        assertEquals(view.filterIntent(), just)
        verify(view, times(2)).filterIntent()
        verify(artistReleasesTransformer, times(1)).setFilterText(filterText)
        verify(mockRelay, times(2)).value
        verifyNoMoreInteractions(mockRelay)
    }

    @Test
    fun setupFilterRelayItem_setsFilterTextEmits() {
        val mockRelay: BehaviorRelay<MutableList<ArtistRelease>> = mock()
        presenter.setBehaviorRelay(mockRelay)
        val filterText = "filterText"
        val artistRelease = ArtistRelease()
        val list = mutableListOf(artistRelease)
        val just = Observable.just<CharSequence>(filterText)
        whenever(view.filterIntent()).thenReturn(just)
        whenever(mockRelay.value).thenReturn(list)

        presenter.setupFilter()

        assertEquals(view.filterIntent(), just)
        verify(view, times(2)).filterIntent()
        verify(artistReleasesTransformer, times(1)).setFilterText(filterText)
        verify(mockRelay, times(3)).value
        verify(mockRelay, times(1)).accept(list)
    }
}