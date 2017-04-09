package bj.rxjavaexperimentation.model;

import io.reactivex.Observable;

/**
 * Created by Josh Laird on 05/04/2017.
 */

public class ResultModel
{
    private String type;
    private Observable observable;

    public ResultModel(String type, Observable observable)
    {
        this.type = type;
        this.observable = observable;
    }

    public Observable getObservable()
    {
        return observable;
    }
}
