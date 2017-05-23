package bj.vinylbrowser.master

import bj.vinylbrowser.model.master.Master
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
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
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class MasterPresenterTest {
    lateinit var presenter: MasterPresenter
    val view: MasterContract.View = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val controller: MasterController = mock()
    val testScheduler = TestScheduler()
    val id = "123"

    @Before
    fun setUp() {
        presenter = MasterPresenter(discogsInteractor, controller, TestSchedulerProvider(testScheduler))
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, discogsInteractor, controller)
    }

    @Test
    fun getValidData_getsDetailsAndVersions() {
        val master = Master(id = id)
        val justMaster = Single.just(master)
        val masterVersions = MasterVersionsFactory.buildMasterVersions(2)
        val justVersions = Single.just(masterVersions)

        whenever(discogsInteractor.fetchMasterDetails(id)).thenReturn(justMaster)
        whenever(discogsInteractor.fetchMasterVersions(id)).thenReturn(justVersions)

        presenter.fetchReleaseDetails(id)
        testScheduler.triggerActions()

        verify(controller).setLoading(true)
        verify(discogsInteractor).fetchMasterDetails(id)
        verify(controller).setMaster(master)
        verify(discogsInteractor).fetchMasterVersions(id)
        verify(controller).setMasterMasterVersions(masterVersions)
    }

    @Test
    fun getInvalidData_controllerSetsError() {
        whenever(discogsInteractor.fetchMasterDetails(id)).thenReturn(Single.error<Master>(Throwable()))

        presenter.fetchReleaseDetails(id)
        testScheduler.triggerActions()

        verify(controller).setLoading(true)
        verify(discogsInteractor).fetchMasterDetails(id)
        verify(controller).setError(true)
    }
}