package bj.discogsbrowser.utils.schedulerprovider

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

/**
 * Created by Josh Laird on 22/05/2017.
 */
class TestSchedulerProvider(val testScheduler: TestScheduler) : MySchedulerProvider() {
    override fun ui(): Scheduler {
        return testScheduler
    }

    override fun computation(): Scheduler {
        return testScheduler
    }

    override fun trampoline(): Scheduler {
        return testScheduler
    }

    override fun newThread(): Scheduler {
        return testScheduler
    }

    override fun io(): Scheduler {
        return testScheduler
    }
}