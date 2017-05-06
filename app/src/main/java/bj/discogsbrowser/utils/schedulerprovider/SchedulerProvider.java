package bj.discogsbrowser.utils.schedulerprovider;

import io.reactivex.Scheduler;

/**
 * Created by Josh Laird on 11/04/2017.
 * <p>
 * Inspired by http://stackoverflow.com/a/43320828/4624156
 */
public interface SchedulerProvider
{
    Scheduler ui();

    Scheduler computation();

    Scheduler trampoline();

    Scheduler newThread();

    Scheduler io();
}
