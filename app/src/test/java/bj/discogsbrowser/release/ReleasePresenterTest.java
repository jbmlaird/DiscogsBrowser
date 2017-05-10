//package bj.discogsbrowser.release;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import bj.discogsbrowser.greendao.DaoInteractor;
//import bj.discogsbrowser.network.DiscogsInteractor;
//import bj.discogsbrowser.order.OrderFactory;
//import bj.discogsbrowser.utils.ArtistsBeautifier;
//import bj.discogsbrowser.utils.SharedPrefsManager;
//import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
//import bj.discogsbrowser.wrappers.LogWrapper;
//import io.reactivex.schedulers.TestScheduler;
//
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
///**
// * Created by Josh Laird on 10/05/2017.
// */
//@RunWith(MockitoJUnitRunner.class)
//public class ReleasePresenterTest
//{
//    private ReleasePresenter presenter;
//    @Mock ReleaseContract.View view;
//    @Mock ReleaseController controller;
//    @Mock DiscogsInteractor discogsInteractor;
//    private TestScheduler testScheduler = new TestScheduler();
//    @Mock SharedPrefsManager sharedPrefsManager;
//    @Mock LogWrapper logWrapper;
//    @Mock DaoInteractor daoInteractor;
//    @Mock ArtistsBeautifier artistsBeautifier;
//    private OrderFactory orderFactory = new OrderFactory();
//
//    @Before
//    public void setUp()
//    {
//        presenter = new ReleasePresenter(view, controller, discogsInteractor, new TestSchedulerProvider(testScheduler),
//                sharedPrefsManager, logWrapper, daoInteractor, artistsBeautifier);
//    }
//
//    @After
//    public void tearDown()
//    {
//        verifyNoMoreInteractions(view, controller, discogsInteractor,
//                sharedPrefsManager, logWrapper, daoInteractor, artistsBeautifier);
//    }
//}
