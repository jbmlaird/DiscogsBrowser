package bj.discogsbrowser.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyControllerAdapter;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
    private OrderFactory orderFactory = new OrderFactory();

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
    public void setupRecyclerView_setsUpRecyclerView()
    {
        OrderActivity mockOrderActivity = mock(OrderActivity.class);
        RecyclerView mockRecyclerView = mock(RecyclerView.class);
        EpoxyControllerAdapter epoxyControllerAdapter = mock(EpoxyControllerAdapter.class);
        when(controller.getAdapter()).thenReturn(epoxyControllerAdapter);

        presenter.setupRecyclerView(mockOrderActivity, mockRecyclerView);

        verify(mockRecyclerView).setLayoutManager(any(LinearLayoutManager.class));
        verify(controller).getAdapter();
        verify(mockRecyclerView).setAdapter(epoxyControllerAdapter);
    }

    @Test
    public void getValidOrderDetails_displaysDetails()
    {
        String orderId = orderFactory.getOrderId();
        Order order = orderFactory.getOneItemOrder();
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
        String orderId = orderFactory.getOrderId();
        when(discogsInteractor.fetchOrderDetails(orderId)).thenReturn(Single.error(new Throwable()));

        presenter.fetchOrderDetails(orderId);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchOrderDetails(orderId);
        verify(controller).setLoadingOrder(true);
        verify(controller).setError(true);
    }
}
