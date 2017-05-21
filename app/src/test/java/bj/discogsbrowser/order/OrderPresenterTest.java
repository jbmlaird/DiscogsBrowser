package bj.discogsbrowser.order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderPresenterTest
{
    private OrderPresenter presenter;
    @Mock DiscogsInteractor discogsInteractor;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock OrderController controller;

    @Before
    public void setUp()
    {
        presenter = new OrderPresenter(discogsInteractor, new TestSchedulerProvider(testScheduler), controller);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(discogsInteractor, controller);
    }

    @Test
    public void getValidOrderDetails_displaysDetails()
    {
        Order order = OrderFactory.buildOneOrderWithItems(1);
        String orderId = order.getId();
        Single<Order> just = Single.just(order);
        when(discogsInteractor.fetchOrderDetails(orderId)).thenReturn(just);

        presenter.fetchOrderDetails(orderId);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchOrderDetails(orderId);
        verify(controller).setLoadingOrder(true);
        verify(controller).setOrderDetails(order);
    }

    @Test
    public void getInvalidOrderDetails_displaysError()
    {
        Order order = OrderFactory.buildOneOrderWithItems(1);
        String orderId = order.getId();
        when(discogsInteractor.fetchOrderDetails(orderId)).thenReturn(Single.error(new Throwable()));

        presenter.fetchOrderDetails(orderId);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchOrderDetails(orderId);
        verify(controller).setLoadingOrder(true);
        verify(controller).setError(true);
    }
}
