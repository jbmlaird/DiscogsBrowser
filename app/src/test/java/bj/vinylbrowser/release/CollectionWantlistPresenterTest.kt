package bj.vinylbrowser.release

import android.content.Context
import bj.vinylbrowser.epoxy.release.CollectionWantlistPresenter
import bj.vinylbrowser.model.collection.AddToCollectionResponse
import bj.vinylbrowser.model.wantlist.AddToWantlistResponse
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.SharedPrefsManager
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import bj.vinylbrowser.wrappers.ToastyWrapper
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class CollectionWantlistPresenterTest {
    lateinit var presenter: CollectionWantlistPresenter
    val context: Context = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val sharedPrefsManager: SharedPrefsManager = mock()
    val testScheduler = TestScheduler()
    val toasty: ToastyWrapper = mock()
    val btnWantlist: CircularProgressButton = mock()
    val btnCollection: CircularProgressButton = mock()
    val releaseId = "123"
    val instanceId = "instanceId"
    val unableToAddToCollection = "Unable to add to Collection"
    val unableToRemoveFromCollection = "Unable to remove from Collection"
    val unableToAddToWantlist = "Unable to add to Wantlist"
    val unableToRemoveFromWantlist = "Unable to remove from Wantlist"

    @Before
    fun setUp() {
        presenter = CollectionWantlistPresenter(context, discogsInteractor,
                sharedPrefsManager, TestSchedulerProvider(testScheduler), toasty)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(context, discogsInteractor, sharedPrefsManager, toasty, btnWantlist, btnCollection)
    }

    @Test
    fun bind_binds() {
        presenter.bind(true, true, "ye", releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInCollection)
        assertTrue(presenter.isInWantlist)

        presenter.bind(false, false, "ye", releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInCollection)
        assertFalse(presenter.isInWantlist)

        presenter.bind(true, false, "ye", releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInCollection)
        assertFalse(presenter.isInWantlist)

        presenter.bind(false, true, "ye", releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInCollection)
        assertTrue(presenter.isInWantlist)
    }

    @Test
    fun addToCollectionSuccess_success() {
        whenever(discogsInteractor.addToCollection(releaseId)).thenReturn(Single.just<AddToCollectionResponse>(ResponseFactory.buildAddToCollectionSuccessResponse()))

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInCollection)
        presenter.addToCollection()
        testScheduler.triggerActions()
        assertTrue(presenter.isInCollection)

        verify(sharedPrefsManager).setFetchNextCollection("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).addToCollection(releaseId)
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener::class.java))
        verifyNoMoreInteractions(btnCollection)
    }

    @Test
    fun addToCollectionNoInstanceId_error() {
        whenever(discogsInteractor.addToCollection(releaseId)).thenReturn(Single.just<AddToCollectionResponse>(ResponseFactory.buildAddToCollectionBadResponse()))

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInCollection)
        presenter.addToCollection()
        testScheduler.triggerActions()
        assertFalse(presenter.isInCollection)

        verify(sharedPrefsManager).setFetchNextCollection("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).addToCollection(releaseId)
        verify(toasty).error(unableToAddToCollection)
        verify(btnCollection).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verifyNoMoreInteractions(btnCollection)
    }

    @Test
    fun addToCollectionError_error() {
        whenever(discogsInteractor.addToCollection(releaseId)).thenReturn(Single.error<AddToCollectionResponse>(Throwable()))

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInCollection)
        presenter.addToCollection()
        testScheduler.triggerActions()
        assertFalse(presenter.isInCollection)

        verify(sharedPrefsManager).setFetchNextCollection("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).addToCollection(releaseId)
        verify(toasty).error(unableToAddToCollection)
        verify(btnCollection).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verifyNoMoreInteractions(btnCollection)
    }

    @Test
    fun removeFromCollectionSuccess_success() {
        whenever(discogsInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.just<Response<Void>>(ResponseFactory.getRetrofitSuccessfulResponse()))

        presenter.bind(true, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInCollection)
        presenter.removeFromCollection()
        testScheduler.triggerActions()
        assertFalse(presenter.isInCollection)

        verify(sharedPrefsManager).setFetchNextCollection("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).removeFromCollection(releaseId, instanceId)
        verify(btnCollection).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verifyNoMoreInteractions(btnCollection)
    }

    @Test
    fun removeFromCollectionUnsuccessfulResponse_error() {
        whenever(discogsInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.just<Response<Void>>(ResponseFactory.getRetrofitBadResponse()))

        presenter.bind(true, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInCollection)
        presenter.removeFromCollection()
        testScheduler.triggerActions()
        assertTrue(presenter.isInCollection)

        verify(sharedPrefsManager).setFetchNextCollection("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).removeFromCollection(releaseId, instanceId)
        verify(btnCollection).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verify(toasty).error(unableToRemoveFromCollection)
        verifyNoMoreInteractions(btnCollection)
    }

    @Test
    fun RemoveFromCollectionError_error() {
        whenever(discogsInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.error<Response<Void>>(Throwable()))

        presenter.bind(true, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInCollection)
        presenter.removeFromCollection()
        testScheduler.triggerActions()
        assertTrue(presenter.isInCollection)

        verify(sharedPrefsManager).setFetchNextCollection("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).removeFromCollection(releaseId, instanceId)
        verify(btnCollection).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verify(toasty).error(unableToRemoveFromCollection)
        verifyNoMoreInteractions(btnCollection)
    }

    @Test
    fun addToWantlistSuccess_success() {
        whenever(discogsInteractor.addToWantlist(releaseId)).thenReturn(Single.just<AddToWantlistResponse>(ResponseFactory.buildAddToWantlistSuccessResponse()))

        presenter.bind(false, false, instanceId, releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInWantlist)
        presenter.addToWantlist()
        testScheduler.triggerActions()
        assertTrue(presenter.isInWantlist)

        verify(sharedPrefsManager).setFetchNextWantlist("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).addToWantlist(releaseId)
        verify(btnWantlist).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verifyNoMoreInteractions(btnWantlist)
    }

    @Test
    fun addToWantlistError_displaysError() {
        whenever(discogsInteractor.addToWantlist(releaseId)).thenReturn(Single.error<AddToWantlistResponse>(Throwable()))

        presenter.bind(false, false, instanceId, releaseId, btnWantlist, btnCollection)
        assertFalse(presenter.isInWantlist)
        presenter.addToWantlist()
        testScheduler.triggerActions()
        assertFalse(presenter.isInWantlist)

        verify(sharedPrefsManager).setFetchNextWantlist("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).addToWantlist(releaseId)
        verify(btnWantlist).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verify(toasty).error(unableToAddToWantlist)
        verifyNoMoreInteractions(btnWantlist)
    }

    @Test
    fun removeFromWantlistSuccess_success() {
        whenever(discogsInteractor.removeFromWantlist(releaseId)).thenReturn(Single.just<Response<Void>>(ResponseFactory.getRetrofitSuccessfulResponse()))

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInWantlist)
        presenter.removeFromWantlist()
        testScheduler.triggerActions()
        assertFalse(presenter.isInWantlist)

        verify(sharedPrefsManager).setFetchNextWantlist("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).removeFromWantlist(releaseId)
        verify(btnWantlist).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verifyNoMoreInteractions(btnWantlist)
    }

    @Test
    fun removeFromWantlistResponseError_displaysError() {
        whenever(discogsInteractor.removeFromWantlist(releaseId)).thenReturn(Single.just<Response<Void>>(ResponseFactory.getRetrofitBadResponse()))

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInWantlist)
        presenter.removeFromWantlist()
        testScheduler.triggerActions()
        assertTrue(presenter.isInWantlist)

        verify(sharedPrefsManager).setFetchNextWantlist("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).removeFromWantlist(releaseId)
        verify(btnWantlist).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verify(toasty).error(unableToRemoveFromWantlist)
    }

    @Test
    fun removeFromWantlistSingleError_displaysError() {
        whenever(discogsInteractor.removeFromWantlist(releaseId)).thenReturn(Single.error<Response<Void>>(Throwable()))

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection)
        assertTrue(presenter.isInWantlist)
        presenter.removeFromWantlist()
        testScheduler.triggerActions()
        assertTrue(presenter.isInWantlist)

        verify(sharedPrefsManager).setFetchNextWantlist("yes")
        verify(sharedPrefsManager).setfetchNextUserDetails("yes")
        verify(discogsInteractor, times(1)).removeFromWantlist(releaseId)
        verify(btnWantlist).revertAnimation(any<OnAnimationEndListener>(OnAnimationEndListener::class.java))
        verify(toasty).error(unableToRemoveFromWantlist)
        verifyNoMoreInteractions(btnWantlist)
    }
}