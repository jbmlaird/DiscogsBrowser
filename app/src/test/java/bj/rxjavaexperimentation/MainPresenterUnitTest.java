//package bj.rxjavaexperimentation;
//
//import android.support.v7.widget.RecyclerView;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import bj.rxjavaexperimentation.discogs.DiscogsInteractor;
//import bj.rxjavaexperimentation.discogs.gson.release.Release;
//import bj.rxjavaexperimentation.main.MainContract;
//import bj.rxjavaexperimentation.main.MainPresenter;
//import io.reactivex.Observable;
//import io.reactivex.android.plugins.RxAndroidPlugins;
//import io.reactivex.schedulers.Schedulers;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(MockitoJUnitRunner.class)
//public class MainPresenterUnitTest
//{
//    private String searchQuery = "searchQuery";
//
//    @Mock
//    DiscogsInteractor discogsInteractor;
//
//    @Mock
//    MainContract.View mView;
//
//    @Mock
//    RecyclerView recyclerView;
//
//    @Mock
//    Observable<Release> observable;
//
//    @InjectMocks
//    MainPresenter mainPresenter;
//
//    @Test
//    public void search_showsProgressBar() throws Exception
//    {
//        mainPresenter.setView(mView);
//        mainPresenter.onSearchAction(searchQuery);
//        verify(mView, times(1)).showProgressBar();
//    }
//
//    @Test
//    public void setUpRecyclerView_SetsUpCorrectly()
//    {
//        mainPresenter.setupRecyclerView(recyclerView);
//        verify(recyclerView, times(1)).setLayoutManager(any(RecyclerView.LayoutManager.class));
//        verify(recyclerView, times(1)).setAdapter(any(RecyclerView.Adapter.class));
//    }
//
//    /**
//     * Pulled from blog: https://medium.com/@peter.tackage/overriding-rxandroid-schedulers-in-rxjava-2-5561b3d14212
//     */
//    @BeforeClass
//    public static void setupThreads()
//    {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
//                __ -> Schedulers.trampoline());
//    }
//
//    @Test
//    public void test()
//    {
////        mainPresenter.addToRecyclerView(observable);
////        verify(mView).hideProgressBar();
//    }
//}