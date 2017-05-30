package bj.vinylbrowser.artist

import android.support.v7.widget.RecyclerView
import bj.vinylbrowser.artistreleases.ArtistResultFactory
import bj.vinylbrowser.model.artist.ArtistResult
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.rxmodifiers.RemoveUnwantedLinksFunction
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import bj.vinylbrowser.wrappers.LogWrapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class ArtistPresenterTest {

    val view: ArtistContract.View = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    lateinit var testScheduler: TestScheduler
    val logWrapper: LogWrapper = mock()
    val artistController: ArtistEpxController = mock()
    val recyclerView: RecyclerView = mock()
    val unwantedLinksFunction: RemoveUnwantedLinksFunction = mock()

    lateinit var artistPresenter: ArtistPresenter
    private val myId = "12345"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        artistPresenter = ArtistPresenter(view, discogsInteractor, testSchedulerProvider, logWrapper, artistController, unwantedLinksFunction)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, discogsInteractor, logWrapper, artistController, unwantedLinksFunction)
    }

    @Test
    @Throws(Exception::class)
    fun getsDataValid_controllerSetsArtist() {
        val testArtistResult = ArtistResultFactory.buildArtistResult(0)
        whenever(discogsInteractor.fetchArtistDetails(myId)).thenReturn(Single.just(testArtistResult))
        whenever(unwantedLinksFunction.apply(testArtistResult)).thenReturn(testArtistResult)

        artistPresenter.fetchArtistDetails(myId)
        testScheduler.triggerActions()

        verify(unwantedLinksFunction).apply(testArtistResult)
        verify(discogsInteractor).fetchArtistDetails(myId)
        verify(artistController).setLoading(true)
        verify(artistController).setArtist(testArtistResult)
        verify(logWrapper).e(any(String::class.java), any(String::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun getsDataError_setsControllerError() {
        whenever(discogsInteractor.fetchArtistDetails(myId)).thenReturn(Single.error<ArtistResult>(Exception()))

        artistPresenter.fetchArtistDetails(myId)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchArtistDetails(myId)
        verify(artistController).setLoading(true)
        verify(artistController).setError(true)
        verify(logWrapper).e(any(String::class.java), any(String::class.java))
    }
}