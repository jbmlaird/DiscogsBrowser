package bj.discogsbrowser.master;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import bj.discogsbrowser.model.master.Master;
import bj.discogsbrowser.model.version.Version;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MasterPresenterTest
{
    private MasterPresenter presenter;
    @Mock MasterContract.View view;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock MasterController controller;
    private TestScheduler testScheduler = new TestScheduler();
    private String id = "123";

    @Before
    public void setUp()
    {
        presenter = new MasterPresenter(discogsInteractor, controller, new TestSchedulerProvider(testScheduler));
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, controller);
    }

    @Test
    public void getValidData_getsDetailsAndVersions()
    {
        Master master = new TestMaster();
        Single<Master> justMaster = Single.just(master);
        List<Version> versions = MasterVersionsFactory.getTwoMasterVersions();
        Single<List<Version>> justVersions = Single.just(versions);

        when(discogsInteractor.fetchMasterDetails(id)).thenReturn(justMaster);
        when(discogsInteractor.fetchMasterVersions(id)).thenReturn(justVersions);

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(controller).setLoading(true);
        verify(discogsInteractor).fetchMasterDetails(id);
        verify(controller).setMaster(master);
        verify(discogsInteractor).fetchMasterVersions(id);
        verify(controller).setMasterVersions(versions);
    }

    @Test
    public void getInvalidData_controllerSetsError()
    {
        when(discogsInteractor.fetchMasterDetails(id)).thenReturn(Single.error(new Throwable()));

        presenter.getReleaseAndLabelDetails(id);
        testScheduler.triggerActions();

        verify(controller).setLoading(true);
        verify(discogsInteractor).fetchMasterDetails(id);
        verify(controller).setError(true);
    }
}
