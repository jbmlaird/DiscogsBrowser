package bj.discogsbrowser.order

import bj.discogsbrowser.model.order.Order
import bj.discogsbrowser.network.DiscogsInteractor
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider
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
    val controller: OrderController = mock()

    @Before
    fun setUp() {
        presenter = OrderPresenter(discogsInteractor, TestSchedulerProvider(testScheduler), controller)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(discogsInteractor, controller)
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
        verify(controller).setLoadingOrder(true)
        verify(controller).setOrderDetails(order)
    }

    @Test
    fun getInvalidOrderDetails_displaysError() {
        val order = OrderFactory.buildOneOrderWithItems(1)
        val orderId = order.getId()
        whenever(discogsInteractor.fetchOrderDetails(orderId)).thenReturn(Single.error<Order>(Throwable()))

        presenter.fetchOrderDetails(orderId)
        testScheduler.triggerActions()

        verify(discogsInteractor).fetchOrderDetails(orderId)
        verify(controller).setLoadingOrder(true)
        verify(controller).setError(true)
    }
}