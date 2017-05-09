//package bj.discogsbrowser.search;
//
//import android.content.Context;
//
//import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.List;
//
//import bj.discogsbrowser.model.search.SearchResult;
//import bj.discogsbrowser.greendao.DaoInteractor;
//import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
//import io.reactivex.ObservableSource;
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.functions.Function;
//
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
///**
// * Created by Josh Laird on 02/05/2017.
// */
//@RunWith(MockitoJUnitRunner.class)
//public class SearchPresenterTest
//{
//    private SearchPresenter presenter;
//    @Mock Context mContext;
//    @Mock SearchContract.View mView;
//    @Mock SearchController searchController;
//    @Mock Function<SearchViewQueryTextEvent, ObservableSource<List<SearchResult>>> searchModelFunc;
//    @Mock MySchedulerProvider mySchedulerProvider;
//    @Mock DaoInteractor daoInteractor;
//    @Mock CompositeDisposable disposable;
//
//    @Before
//    public void setUp()
//    {
//        presenter = new SearchPresenter(mContext, mView, searchController, searchModelFunc, mySchedulerProvider, daoInteractor, disposable);
//        presenter.setupSubscriptions();
//    }
//
//    @After
//    public void tearDown()
//    {
//        verifyNoMoreInteractions(mContext, mView, searchController, searchModelFunc, mySchedulerProvider, daoInteractor, disposable);
//    }
//
//    // SearchPresenter is very UI-based with the double RxBindings
//}
