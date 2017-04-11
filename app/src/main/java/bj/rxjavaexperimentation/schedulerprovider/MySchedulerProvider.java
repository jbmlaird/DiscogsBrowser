package bj.rxjavaexperimentation.schedulerprovider;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class MySchedulerProvider implements SchedulerProvider
{
    @Inject
    public MySchedulerProvider()
    {

    }

    @Override
    public Scheduler ui()
    {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation()
    {
        return Schedulers.computation();
    }

    @Override
    public Scheduler trampoline()
    {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler newThread()
    {
        return Schedulers.newThread();
    }

    @Override
    public Scheduler io()
    {
        return Schedulers.io();
    }
}
