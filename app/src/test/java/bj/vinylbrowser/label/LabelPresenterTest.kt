package bj.vinylbrowser.label

import bj.vinylbrowser.model.common.Label
import bj.vinylbrowser.model.labelrelease.LabelRelease
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class LabelPresenterTest {
    val controller: LabelEpxController = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val testScheduler = TestScheduler()
    lateinit var presenter: LabelPresenter
    val id = "123"

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter = LabelPresenter(controller, discogsInteractor, TestSchedulerProvider(testScheduler))
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(controller, discogsInteractor)
    }

    @Test
    fun fetchLabelDetailsNoReleasesSuccess_controllerDisplays() {
        val label = Label()
        val emptyList = emptyList<LabelRelease>()
        whenever(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(label))
        whenever(discogsInteractor.fetchLabelReleases(id)).thenReturn(Single.just(emptyList))

        presenter.fetchReleaseDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchLabelDetails(id)
        verify(controller, times(1)).setLoading(true)
        verify(controller).setLabel(label)
        verify(discogsInteractor, times(1)).fetchLabelReleases(id)
        verify(controller, times(1)).setLabelReleases(emptyList)
    }

    @Test
    fun fetchLabelDetailsReleasesSuccess_controllerDisplays() {
        val label = Label()
        val labelReleases = mutableListOf<LabelRelease>()
        labelReleases.add(LabelRelease())
        labelReleases.add(LabelRelease())
        whenever(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.just(label))
        whenever(discogsInteractor.fetchLabelReleases(id)).thenReturn(Single.just<List<LabelRelease>>(labelReleases))

        presenter.fetchReleaseDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchLabelDetails(id)
        verify(controller, times(1)).setLoading(true)
        verify(controller).setLabel(label)
        verify(discogsInteractor, times(1)).fetchLabelReleases(id)
        verify(controller, times(1)).setLabelReleases(labelReleases)
    }

    @Test
    fun fetchLabelDetailsError_controllerError() {
        whenever(discogsInteractor.fetchLabelDetails(id)).thenReturn(Single.error<Label>(Throwable()))

        presenter.fetchReleaseDetails(id)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchLabelDetails(id)
        verify(controller, times(1)).setLoading(true)
        verify(controller).setError(true)
    }
}