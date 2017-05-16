package bj.discogsbrowser.singlelist;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.R;
import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.order.OrderFactory;
import bj.discogsbrowser.utils.FilterHelper;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 08/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SingleListPresenterTest
{
    private String username = "bjlairy";
    private String errorWantlistString = "Unable to load Wantlist";
    private String wantlistNoneString = "Nothing in your Wantlist yet";
    private String errorCollectionString = "Unable to load Collection";
    private String collectionNoneString = "Nothing in your Collection yet";
    private String errorOrdersString = "Unable to load orders";
    private String ordersNoneString = "Not sold anything";
    private String errorSellingString = "Unable to load selling";
    private String sellingNoneString = "Not selling anything";

    private SingleListPresenter singleListPresenter;
    @Mock Context context;
    @Mock SingleListContract.View view;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock CollectionWantlistInteractor collectionWantlistInteractor;
    private TestScheduler testScheduler;
    @Mock SingleListController controller;
    @Mock CompositeDisposable compositeDisposable;
    @Mock FilterHelper filterHelper;

    @Before
    public void setup()
    {
        testScheduler = new TestScheduler();
        singleListPresenter = new SingleListPresenter(context, view, discogsInteractor, collectionWantlistInteractor,
                new TestSchedulerProvider(testScheduler), controller, compositeDisposable, filterHelper);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(context, view, discogsInteractor, controller, compositeDisposable);
    }

//    @Test
//    public void setupRecyclerView_setsUpRecyclerView()
//    {
//        SingleListActivity mockActivity = mock(SingleListActivity.class);
//        RecyclerView mockRecyclerView = mock(RecyclerView.class);
//        EpoxyControllerAdapter mockEpoxyControllerAdapter = mock(EpoxyControllerAdapter.class);
//        when(controller.getAdapter()).thenReturn(mockEpoxyControllerAdapter);
//
//        singleListPresenter.setupRecyclerView(mockActivity, mockRecyclerView);
//
//        verify(mockRecyclerView).setLayoutManager(any(LinearLayoutManager.class));
//        verify(controller, times(1)).getAdapter();
//        verify(mockRecyclerView).setAdapter(mockEpoxyControllerAdapter);
//        verify(controller, times(1)).requestModelBuild();
//    }

    @Test
    public void setupFilterSubscriptionNoItems_setsUpFilterSubscription()
    {
        String filterText = "yeson";
        when(compositeDisposable.add(any(Disposable.class))).thenReturn(true);
        when(view.filterIntent()).thenReturn(Observable.just(filterText));

        singleListPresenter.setupFilterSubscription();
        testScheduler.triggerActions();

        verify(compositeDisposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).filterIntent();
        verify(filterHelper, times(1)).setFilterText(filterText);
    }

    @Test
    public void setupFilterSubscriptionItemsNoFilter_showsItems()
    {
        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();
        recyclerViewModels.add(OrderFactory.getOneItemOrder());

        singleListPresenter.setItems(recyclerViewModels);
        String filterText = "yeson";
        when(compositeDisposable.add(any(Disposable.class))).thenReturn(true);
        when(view.filterIntent()).thenReturn(Observable.just(filterText));
        // Don't filter
        when(filterHelper.filterByFilterText()).thenReturn(o -> o);

        singleListPresenter.setupFilterSubscription();
        testScheduler.triggerActions();

        verify(compositeDisposable, times(1)).add(any(Disposable.class));
        verify(view, times(1)).filterIntent();
        verify(filterHelper).filterByFilterText();
        verify(filterHelper, times(1)).setFilterText(filterText);
        verify(controller).setItems(recyclerViewModels);
    }

    @Test
    public void unsubDispose_unsubsDisposes()
    {
        singleListPresenter.unsubscribe();
        singleListPresenter.dispose();

        verify(compositeDisposable, times(1)).clear();
        verify(compositeDisposable, times(1)).dispose();
    }

    @Test
    public void getDataWantlistError_showsError()
    {
        Single error = Single.error(new Throwable());
        when(collectionWantlistInteractor.fetchWantlist(username)).thenReturn(error);
        when(context.getString(R.string.error_wantlist)).thenReturn(errorWantlistString);
        when(context.getString(R.string.wantlist_none)).thenReturn(wantlistNoneString);
        // Test filter in filter class - don't filter here
        singleListPresenter.getData(R.string.drawer_item_wantlist, username);

        testScheduler.triggerActions();

        verify(collectionWantlistInteractor, times(1)).fetchWantlist(username);
        assertEquals(context.getString(R.string.error_wantlist), errorWantlistString);
        verify(context, times(2)).getString(R.string.error_wantlist);
        assertEquals(context.getString(R.string.wantlist_none), wantlistNoneString);
        verify(context, times(1)).getString(R.string.wantlist_none);
        verify(controller, times(1)).setError(errorWantlistString);
    }

    @Test
    public void getDataCollectionError_showsError()
    {
        Single error = Single.error(new Throwable());
        when(collectionWantlistInteractor.fetchCollection(username)).thenReturn(error);
        when(context.getString(R.string.error_collection)).thenReturn(errorCollectionString);
        when(context.getString(R.string.collection_none)).thenReturn(collectionNoneString);

        singleListPresenter.getData(R.string.drawer_item_collection, username);
        testScheduler.triggerActions();

        verify(collectionWantlistInteractor, times(1)).fetchCollection(username);
        assertEquals(context.getString(R.string.error_collection), errorCollectionString);
        verify(context, times(2)).getString(R.string.error_collection);
        assertEquals(context.getString(R.string.collection_none), collectionNoneString);
        verify(context, times(1)).getString(R.string.collection_none);
        verify(controller, times(1)).setError(errorCollectionString);
    }

    @Test
    public void getDataOrdersError_showsError()
    {
        Single error = Single.error(new Throwable());
        when(discogsInteractor.fetchOrders()).thenReturn(error);
        when(context.getString(R.string.error_orders)).thenReturn(errorOrdersString);
        when(context.getString(R.string.orders_none)).thenReturn(ordersNoneString);

        singleListPresenter.getData(R.string.orders, username);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchOrders();
        assertEquals(context.getString(R.string.error_orders), errorOrdersString);
        verify(context, times(2)).getString(R.string.error_orders);
        assertEquals(context.getString(R.string.orders_none), ordersNoneString);
        verify(context, times(1)).getString(R.string.orders_none);
        verify(controller, times(1)).setError(errorOrdersString);
    }

    @Test
    public void getDataSellingError_showsError()
    {
        Single error = Single.error(new Throwable());
        when(discogsInteractor.fetchSelling(username)).thenReturn(error);
        when(context.getString(R.string.error_selling)).thenReturn(errorSellingString);
        when(context.getString(R.string.selling_none)).thenReturn(sellingNoneString);
        when(filterHelper.filterForSale()).thenReturn(o -> o);

        singleListPresenter.getData(R.string.selling, username);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchSelling(username);
        assertEquals(context.getString(R.string.error_selling), errorSellingString);
        verify(context, times(2)).getString(R.string.error_selling);
        assertEquals(context.getString(R.string.selling_none), sellingNoneString);
        verify(context, times(1)).getString(R.string.selling_none);
        verify(controller, times(1)).setError(errorSellingString);
    }

    @Test
    public void getDataNoItems_displaysEmptyList()
    {
        ArrayList<Listing> emptyList = new ArrayList<>();
        when(discogsInteractor.fetchSelling(username)).thenReturn(Single.just(emptyList));
        when(context.getString(R.string.error_selling)).thenReturn(errorSellingString);
        when(context.getString(R.string.selling_none)).thenReturn(sellingNoneString);
        when(filterHelper.filterForSale()).thenReturn(o -> o);
        when(filterHelper.filterByFilterText()).thenReturn(o -> o);

        singleListPresenter.getData(R.string.selling, username);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchSelling(username);
        assertEquals(context.getString(R.string.error_selling), errorSellingString);
        verify(context, times(2)).getString(R.string.error_selling);
        assertEquals(context.getString(R.string.selling_none), sellingNoneString);
        verify(filterHelper, times(1)).filterByFilterText();
        verify(context, times(1)).getString(R.string.selling_none);
        verify(controller).setItems(emptyList);
    }
}