package bj.vinylbrowser.singlelist

import android.content.Context
import bj.vinylbrowser.R
import bj.vinylbrowser.model.collection.CollectionRelease
import bj.vinylbrowser.model.common.RecyclerViewModel
import bj.vinylbrowser.model.listing.Listing
import bj.vinylbrowser.model.order.Order
import bj.vinylbrowser.model.wantlist.Want
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.order.OrderFactory
import bj.vinylbrowser.utils.FilterHelper
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class SingleListPresenterTest {
    val username = "bjlairy"
    val errorWantlistString = "Unable to load Wantlist"
    val wantlistNoneString = "Nothing in your Wantlist yet"
    val errorCollectionString = "Unable to load Collection"
    val collectionNoneString = "Nothing in your Collection yet"
    val errorOrdersString = "Unable to load orders"
    val ordersNoneString = "Not sold anything"
    val errorSellingString = "Unable to load selling"
    val sellingNoneString = "Not selling anything"
    var singleListPresenter: SingleListPresenter = mock()
    val context: Context = mock()
    val view: SingleListContract.View = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    var testScheduler = TestScheduler()
    val controller: SingleListEpxController = mock()
    val compositeDisposable: CompositeDisposable = mock()
    val filterHelper: FilterHelper = mock()

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        singleListPresenter = SingleListPresenter(context, view, discogsInteractor,
                TestSchedulerProvider(testScheduler), controller, compositeDisposable, filterHelper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(context, view, discogsInteractor, controller, compositeDisposable)
    }

    @Test
    fun setupFilterSubscriptionNoItems_setsUpFilterSubscription() {
        val filterText = "yeson"
        whenever(compositeDisposable.add(any<Disposable>(Disposable::class.java))).thenReturn(true)
        whenever(view.filterIntent()).thenReturn(Observable.just<CharSequence>(filterText))

        singleListPresenter.setupFilterSubscription()
        testScheduler.triggerActions()

        verify(compositeDisposable, times(1)).add(any<Disposable>(Disposable::class.java))
        verify(view, times(1)).filterIntent()
        verify(filterHelper, times(1)).setFilterText(filterText)
    }

    @Test
    fun setupFilterSubscriptionItemsNoFilter_showsItems() {
        val recyclerViewModels = ArrayList<RecyclerViewModel>()
        recyclerViewModels.add(OrderFactory.buildOneOrderWithItems(1))

        singleListPresenter.setItems(recyclerViewModels)
        val filterText = "yeson"
        whenever(compositeDisposable.add(any<Disposable>(Disposable::class.java))).thenReturn(true)
        whenever(view.filterIntent()).thenReturn(Observable.just<CharSequence>(filterText))
        // Don't filter
        whenever(filterHelper.filterByFilterText()).thenReturn(SingleTransformer { o -> o })

        singleListPresenter.setupFilterSubscription()
        testScheduler.triggerActions()

        verify(compositeDisposable, times(1)).add(any<Disposable>(Disposable::class.java))
        verify(view, times(1)).filterIntent()
        verify(filterHelper).filterByFilterText()
        verify(filterHelper, times(1)).setFilterText(filterText)
        verify(controller).setItems(recyclerViewModels)
    }

    @Test
    fun unsubDispose_unsubsDisposes() {
        singleListPresenter.unsubscribe()
        singleListPresenter.dispose()

        verify(compositeDisposable, times(1)).clear()
        verify(compositeDisposable, times(1)).dispose()
    }

    @Test
    fun getDataWantlistError_showsError() {
        val error = Single.error<List<Want>>(Throwable())
        whenever(discogsInteractor.fetchWantlist(username)).thenReturn(error)
        whenever(context.getString(R.string.error_wantlist)).thenReturn(errorWantlistString)
        whenever(context.getString(R.string.wantlist_none)).thenReturn(wantlistNoneString)
        // Test filter in filter class - don't filter here
        singleListPresenter.getData(R.string.drawer_item_wantlist, username)

        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchWantlist(username)
        assertEquals(context.getString(R.string.error_wantlist), errorWantlistString)
        verify(context, times(2)).getString(R.string.error_wantlist)
        assertEquals(context.getString(R.string.wantlist_none), wantlistNoneString)
        verify(context, times(1)).getString(R.string.wantlist_none)
        verify(controller, times(1)).setError(errorWantlistString)
    }

    @Test
    fun getDataCollectionError_showsError() {
        val error = Single.error<List<CollectionRelease>>(Throwable())
        whenever(discogsInteractor.fetchCollection(username)).thenReturn(error)
        whenever(context.getString(R.string.error_collection)).thenReturn(errorCollectionString)
        whenever(context.getString(R.string.collection_none)).thenReturn(collectionNoneString)

        singleListPresenter.getData(R.string.drawer_item_collection, username)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchCollection(username)
        assertEquals(context.getString(R.string.error_collection), errorCollectionString)
        verify(context, times(2)).getString(R.string.error_collection)
        assertEquals(context.getString(R.string.collection_none), collectionNoneString)
        verify(context, times(1)).getString(R.string.collection_none)
        verify(controller, times(1)).setError(errorCollectionString)
    }

    @Test
    fun getDataOrdersError_showsError() {
        val error = Single.error<List<Order>>(Throwable())
        whenever(discogsInteractor.fetchOrders()).thenReturn(error)
        whenever(context.getString(R.string.error_orders)).thenReturn(errorOrdersString)
        whenever(context.getString(R.string.orders_none)).thenReturn(ordersNoneString)

        singleListPresenter.getData(R.string.orders, username)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchOrders()
        assertEquals(context.getString(R.string.error_orders), errorOrdersString)
        verify(context, times(2)).getString(R.string.error_orders)
        assertEquals(context.getString(R.string.orders_none), ordersNoneString)
        verify(context, times(1)).getString(R.string.orders_none)
        verify(controller, times(1)).setError(errorOrdersString)
    }

    @Test
    fun getDataSellingError_showsError() {
        val error = Single.error<List<Listing>>(Throwable())
        whenever(discogsInteractor.fetchSelling(username)).thenReturn(error)
        whenever(context.getString(R.string.error_selling)).thenReturn(errorSellingString)
        whenever(context.getString(R.string.selling_none)).thenReturn(sellingNoneString)
        whenever(filterHelper.filterForSale()).thenReturn(SingleTransformer { o -> o })

        singleListPresenter.getData(R.string.selling, username)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchSelling(username)
        assertEquals(context.getString(R.string.error_selling), errorSellingString)
        verify(context, times(2)).getString(R.string.error_selling)
        assertEquals(context.getString(R.string.selling_none), sellingNoneString)
        verify(context, times(1)).getString(R.string.selling_none)
        verify(controller, times(1)).setError(errorSellingString)
    }

    @Test
    fun getDataNoItems_displaysEmptyList() {
        val emptyList = emptyList<Listing>()
        whenever(discogsInteractor.fetchSelling(username)).thenReturn(Single.just<List<Listing>>(emptyList))
        whenever(context.getString(R.string.error_selling)).thenReturn(errorSellingString)
        whenever(context.getString(R.string.selling_none)).thenReturn(sellingNoneString)
        whenever(filterHelper.filterForSale()).thenReturn(SingleTransformer { o -> o })
        whenever(filterHelper.filterByFilterText()).thenReturn(SingleTransformer { o -> o })

        singleListPresenter.getData(R.string.selling, username)
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchSelling(username)
        assertEquals(context.getString(R.string.error_selling), errorSellingString)
        verify(context, times(2)).getString(R.string.error_selling)
        assertEquals(context.getString(R.string.selling_none), sellingNoneString)
        verify(filterHelper, times(1)).filterByFilterText()
        verify(context, times(1)).getString(R.string.selling_none)
        verify(controller).setItems(emptyList)
    }
}