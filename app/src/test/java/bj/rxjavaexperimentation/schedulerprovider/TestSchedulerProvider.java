package bj.rxjavaexperimentation.schedulerprovider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by Josh Laird on 11/04/2017.
 */
public class TestSchedulerProvider extends MySchedulerProvider
{
    private final TestScheduler testScheduler;

    public TestSchedulerProvider(final TestScheduler testScheduler)
    {
        this.testScheduler = testScheduler;
    }

    @Override
    public Scheduler ui()
    {
        return testScheduler;
    }

    @Override
    public Scheduler computation()
    {
        return testScheduler;
    }

    @Override
    public Scheduler trampoline()
    {
        return testScheduler;
    }

    @Override
    public Scheduler newThread()
    {
        return testScheduler;
    }

    @Override
    public Scheduler io()
    {
        return testScheduler;
    }
}
