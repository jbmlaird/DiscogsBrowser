package bj.discogsbrowser.release;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bj.discogsbrowser.epoxy.release.CollectionWantlistPresenter;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import bj.discogsbrowser.wrappers.ToastyWrapper;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 11/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionWantlistPresenterTest
{
    private CollectionWantlistPresenter presenter;
    @Mock Context context;
    @Mock CollectionWantlistInteractor collectionWantlistInteractor;
    @Mock SharedPrefsManager sharedPrefsManager;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock ToastyWrapper toasty;
    private String releaseId = "1234";
    private String instanceId = "instanceId";
    private ResponseFactory responseFactory = new ResponseFactory();
    private String unableToAddToCollection = "Unable to add to Collection";
    private String unableToRemoveFromCollection = "Unable to remove from Collection";
    private String unableToAddToWantlist = "Unable to add to Wantlist";
    private String unableToRemoveFromWantlist = "Unable to remove from Wantlist";

    @Before
    public void setUp()
    {
        presenter = new CollectionWantlistPresenter(context, collectionWantlistInteractor,
                sharedPrefsManager, new TestSchedulerProvider(testScheduler), toasty);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(context, collectionWantlistInteractor, sharedPrefsManager, toasty);
    }

    @Test
    public void bind_binds()
    {
        presenter.bind(true, true, "ye");
        assertTrue(presenter.isInCollection());
        assertTrue(presenter.isInWantlist());

        presenter.bind(false, false, "ye");
        assertFalse(presenter.isInCollection());
        assertFalse(presenter.isInWantlist());

        presenter.bind(true, false, "ye");
        assertTrue(presenter.isInCollection());
        assertFalse(presenter.isInWantlist());

        presenter.bind(false, true, "ye");
        assertFalse(presenter.isInCollection());
        assertTrue(presenter.isInWantlist());
    }

    @Test
    public void addToCollectionSuccess_success()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.addToCollection(releaseId)).thenReturn(Single.just(responseFactory.getAddToCollectionSuccessfulResponse()));

        presenter.bind(false, true, instanceId);
        assertFalse(presenter.isInCollection());
        presenter.addToCollection(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertTrue(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).addToCollection(releaseId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void addToCollectionNoInstanceId_error()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.addToCollection(releaseId)).thenReturn(Single.just(responseFactory.getAddToCollectionBadResponse()));

        presenter.bind(false, true, instanceId);
        assertFalse(presenter.isInCollection());
        presenter.addToCollection(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertFalse(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).addToCollection(releaseId);
        verify(toasty).error(unableToAddToCollection);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void addToCollectionError_error()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.addToCollection(releaseId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(false, true, instanceId);
        assertFalse(presenter.isInCollection());
        presenter.addToCollection(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertFalse(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).addToCollection(releaseId);
        verify(toasty).error(unableToAddToCollection);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void removeFromCollectionSuccess_success()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.just(responseFactory.getRetrofitSuccessfulResponse()));

        presenter.bind(true, true, instanceId);
        assertTrue(presenter.isInCollection());
        presenter.removeFromCollection(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertFalse(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).removeFromCollection(releaseId, instanceId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void removeFromCollectionUnsuccessfulResponse_error()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.just(responseFactory.getRetrofitBadResponse()));

        presenter.bind(true, true, instanceId);
        assertTrue(presenter.isInCollection());
        presenter.removeFromCollection(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertTrue(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).removeFromCollection(releaseId, instanceId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromCollection);
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void RemoveFromCollectionError_error()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(true, true, instanceId);
        assertTrue(presenter.isInCollection());
        presenter.removeFromCollection(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertTrue(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).removeFromCollection(releaseId, instanceId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromCollection);
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void addToWantlistSuccess_success()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.addToWantlist(releaseId)).thenReturn(Single.just(responseFactory.getAddToWantlistSuccessfulResponse()));

        presenter.bind(false, false, instanceId);
        assertFalse(presenter.isInWantlist());
        presenter.addToWantlist(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertTrue(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).addToWantlist(releaseId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void addToWantlistError_displaysError()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.addToWantlist(releaseId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(false, false, instanceId);
        assertFalse(presenter.isInWantlist());
        presenter.addToWantlist(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertFalse(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).addToWantlist(releaseId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToAddToWantlist);
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void removeFromWantlistSuccess_success()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.removeFromWantlist(releaseId)).thenReturn(Single.just(responseFactory.getRetrofitSuccessfulResponse()));

        presenter.bind(false, true, instanceId);
        assertTrue(presenter.isInWantlist());
        presenter.removeFromWantlist(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertFalse(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).removeFromWantlist(releaseId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void removeFromWantlistResponseError_displaysError()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.removeFromWantlist(releaseId)).thenReturn(Single.just(responseFactory.getRetrofitBadResponse()));

        presenter.bind(false, true, instanceId);
        assertTrue(presenter.isInWantlist());
        presenter.removeFromWantlist(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertTrue(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).removeFromWantlist(releaseId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromWantlist);
        verifyNoMoreInteractions(mockBtn);
    }

    @Test
    public void removeFromWantlistSingleError_displaysError()
    {
        CircularProgressButton mockBtn = mock(CircularProgressButton.class);
        when(collectionWantlistInteractor.removeFromWantlist(releaseId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(false, true, instanceId);
        assertTrue(presenter.isInWantlist());
        presenter.removeFromWantlist(releaseId, mockBtn);
        testScheduler.triggerActions();
        assertTrue(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(collectionWantlistInteractor, times(1)).removeFromWantlist(releaseId);
        verify(mockBtn).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromWantlist);
        verifyNoMoreInteractions(mockBtn);
    }
}
