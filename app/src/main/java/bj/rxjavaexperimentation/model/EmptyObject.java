package bj.rxjavaexperimentation.model;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;

/**
 * Temporary class as @{@link bj.rxjavaexperimentation.search.SearchPresenter}.searchDiscogs() is not yet fully implemented.
 * <p>
 * Created by Josh Laird on 28/02/2017.
 */

public class EmptyObject implements ObservableSource<Object>
{
    @Override
    public void subscribe(Observer<? super Object> observer)
    {

    }
}
