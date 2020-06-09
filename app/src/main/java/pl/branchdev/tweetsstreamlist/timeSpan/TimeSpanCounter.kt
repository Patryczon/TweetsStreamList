package pl.branchdev.tweetsstreamlist.timeSpan

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import pl.branchdev.tweetsstreamlist.rx.SchedulerProvider
import java.util.concurrent.TimeUnit

class TimeSpanCounter(
    timeAmount: Long = 5L,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    schedulerProvider: SchedulerProvider
) : TimeCounter {
    lateinit var timeElapsedAction: () -> Unit
    lateinit var timeDisposable: Disposable
    private var timeCounterDisposable: Single<*> =
        Single.timer(timeAmount, timeUnit, schedulerProvider.computation())
            .subscribeOn(schedulerProvider.computation())
            .observeOn(schedulerProvider.ui())


    override fun startCount() {
        timeDisposable =
            timeCounterDisposable.subscribe({ timeElapsedAction() }, { timeDisposable.dispose() })
    }

    override fun stopCount() {
        timeDisposable.dispose()
    }
}