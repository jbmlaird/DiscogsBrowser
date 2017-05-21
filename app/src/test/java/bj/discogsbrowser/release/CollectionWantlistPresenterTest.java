package bj.discogsbrowser.release;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bj.discogsbrowser.epoxy.release.CollectionWantlistPresenter;
import bj.discogsbrowser.network.DiscogsInteractor;
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
    @Mock DiscogsInteractor DiscogsInteractor;
    @Mock SharedPrefsManager sharedPrefsManager;
    private TestScheduler testScheduler = new TestScheduler();
    @Mock ToastyWrapper toasty;
    @Mock CircularProgressButton btnWantlist;
    @Mock CircularProgressButton btnCollection;
    private String releaseId = "1234";
    private String instanceId = "instanceId";
    private String unableToAddToCollection = "Unable to add to Collection";
    private String unableToRemoveFromCollection = "Unable to remove from Collection";
    private String unableToAddToWantlist = "Unable to add to Wantlist";
    private String unableToRemoveFromWantlist = "Unable to remove from Wantlist";

    @Before
    public void setUp()
    {
        presenter = new CollectionWantlistPresenter(context, DiscogsInteractor,
                sharedPrefsManager, new TestSchedulerProvider(testScheduler), toasty);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(context, DiscogsInteractor, sharedPrefsManager, toasty, btnWantlist, btnCollection);
    }

    @Test
    public void bind_binds()
    {
        presenter.bind(true, true, "ye", releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInCollection());
        assertTrue(presenter.isInWantlist());

        presenter.bind(false, false, "ye", releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInCollection());
        assertFalse(presenter.isInWantlist());

        presenter.bind(true, false, "ye", releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInCollection());
        assertFalse(presenter.isInWantlist());

        presenter.bind(false, true, "ye", releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInCollection());
        assertTrue(presenter.isInWantlist());
    }

    @Test
    public void addToCollectionSuccess_success()
    {
        when(DiscogsInteractor.addToCollection(releaseId)).thenReturn(Single.just(ResponseFactory.buildAddToCollectionSuccessResponse()));

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInCollection());
        presenter.addToCollection();
        testScheduler.triggerActions();
        assertTrue(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).addToCollection(releaseId);
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(btnCollection);
    }

    @Test
    public void addToCollectionNoInstanceId_error()
    {
        when(DiscogsInteractor.addToCollection(releaseId)).thenReturn(Single.just(ResponseFactory.buildAddToCollectionBadResponse()));

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInCollection());
        presenter.addToCollection();
        testScheduler.triggerActions();
        assertFalse(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).addToCollection(releaseId);
        verify(toasty).error(unableToAddToCollection);
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(btnCollection);
    }

    @Test
    public void addToCollectionError_error()
    {
        when(DiscogsInteractor.addToCollection(releaseId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInCollection());
        presenter.addToCollection();
        testScheduler.triggerActions();
        assertFalse(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).addToCollection(releaseId);
        verify(toasty).error(unableToAddToCollection);
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(btnCollection);
    }

    @Test
    public void removeFromCollectionSuccess_success()
    {
        when(DiscogsInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.just(ResponseFactory.getRetrofitSuccessfulResponse()));

        presenter.bind(true, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInCollection());
        presenter.removeFromCollection();
        testScheduler.triggerActions();
        assertFalse(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).removeFromCollection(releaseId, instanceId);
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(btnCollection);
    }

    @Test
    public void removeFromCollectionUnsuccessfulResponse_error()
    {
        when(DiscogsInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.just(ResponseFactory.getRetrofitBadResponse()));

        presenter.bind(true, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInCollection());
        presenter.removeFromCollection();
        testScheduler.triggerActions();
        assertTrue(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).removeFromCollection(releaseId, instanceId);
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromCollection);
        verifyNoMoreInteractions(btnCollection);
    }

    @Test
    public void RemoveFromCollectionError_error()
    {
        when(DiscogsInteractor.removeFromCollection(releaseId, instanceId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(true, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInCollection());
        presenter.removeFromCollection();
        testScheduler.triggerActions();
        assertTrue(presenter.isInCollection());

        verify(sharedPrefsManager).setFetchNextCollection("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).removeFromCollection(releaseId, instanceId);
        verify(btnCollection).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromCollection);
        verifyNoMoreInteractions(btnCollection);
    }

    @Test
    public void addToWantlistSuccess_success()
    {
        when(DiscogsInteractor.addToWantlist(releaseId)).thenReturn(Single.just(ResponseFactory.buildAddToWantlistSuccessResponse()));

        presenter.bind(false, false, instanceId, releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInWantlist());
        presenter.addToWantlist();
        testScheduler.triggerActions();
        assertTrue(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).addToWantlist(releaseId);
        verify(btnWantlist).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(btnWantlist);
    }

    @Test
    public void addToWantlistError_displaysError()
    {
        when(DiscogsInteractor.addToWantlist(releaseId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(false, false, instanceId, releaseId, btnWantlist, btnCollection);
        assertFalse(presenter.isInWantlist());
        presenter.addToWantlist();
        testScheduler.triggerActions();
        assertFalse(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).addToWantlist(releaseId);
        verify(btnWantlist).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToAddToWantlist);
        verifyNoMoreInteractions(btnWantlist);
    }

    @Test
    public void removeFromWantlistSuccess_success()
    {
        when(DiscogsInteractor.removeFromWantlist(releaseId)).thenReturn(Single.just(ResponseFactory.getRetrofitSuccessfulResponse()));

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInWantlist());
        presenter.removeFromWantlist();
        testScheduler.triggerActions();
        assertFalse(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).removeFromWantlist(releaseId);
        verify(btnWantlist).revertAnimation(any(OnAnimationEndListener.class));
        verifyNoMoreInteractions(btnWantlist);
    }

    @Test
    public void removeFromWantlistResponseError_displaysError()
    {
        when(DiscogsInteractor.removeFromWantlist(releaseId)).thenReturn(Single.just(ResponseFactory.getRetrofitBadResponse()));

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInWantlist());
        presenter.removeFromWantlist();
        testScheduler.triggerActions();
        assertTrue(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).removeFromWantlist(releaseId);
        verify(btnWantlist).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromWantlist);
    }

    @Test
    public void removeFromWantlistSingleError_displaysError()
    {
        when(DiscogsInteractor.removeFromWantlist(releaseId)).thenReturn(Single.error(new Throwable()));

        presenter.bind(false, true, instanceId, releaseId, btnWantlist, btnCollection);
        assertTrue(presenter.isInWantlist());
        presenter.removeFromWantlist();
        testScheduler.triggerActions();
        assertTrue(presenter.isInWantlist());

        verify(sharedPrefsManager).setFetchNextWantlist("yes");
        verify(sharedPrefsManager).setfetchNextUserDetails("yes");
        verify(DiscogsInteractor, times(1)).removeFromWantlist(releaseId);
        verify(btnWantlist).revertAnimation(any(OnAnimationEndListener.class));
        verify(toasty).error(unableToRemoveFromWantlist);
        verifyNoMoreInteractions(btnWantlist);
    }
}
