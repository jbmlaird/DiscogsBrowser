package bj.vinylbrowser.order

import bj.vinylbrowser.model.order.Order
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
class OrderPresenterTest {
    lateinit var presenter: OrderPresenter
    val discogsInteractor: DiscogsInteractor = mock()
    val testScheduler = TestScheduler()
    val epxController: OrderEpxController = mock()

    @Before
    fun setUp() {
        presenter = OrderPresenter(discogsInteractor, TestSchedulerProvider(testScheduler), epxController)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(discogsInteractor, epxController)
    }

    @Test
    fun getValidOrderDetails_displaysDetails() {
        val order = OrderFactory.buildOneOrderWithItems(1)
        val orderId = order.getId()
        val just = Single.just(order)
        whenever(discogsInteractor.fetchOrderDetails(orderId)).thenReturn(just)

        presenter.fetchOrderDetails(orderId)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchOrderDetails(orderId)
        verify(epxController).setLoadingOrder(true)
        verify(epxController).setOrderDetails(order)
    }

    @Test
    fun getInvalidOrderDetails_displaysError() {
        val order = OrderFactory.buildOneOrderWithItems(1)
        val orderId = order.getId()
        whenever(discogsInteractor.fetchOrderDetails(orderId)).thenReturn(Single.error<Order>(Throwable()))

        presenter.fetchOrderDetails(orderId)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchOrderDetails(orderId)
        verify(epxController).setLoadingOrder(true)
        verify(epxController).setError(true)
    }
}