package pl.branchdev.tweetsstreamlist.timeSpan

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import pl.branchdev.common.rx.SchedulerProvider
import java.util.concurrent.TimeUnit

class TimeSpanCounter(
    timeAmount: Long = defaultTimeAmount,
    timeUnit: TimeUnit = defaultTimeUnit,
    schedulerProvider: SchedulerProvider
) : TimeCounter {
    companion object {
        const val defaultTimeAmount = 5L
        val defaultTimeUnit = TimeUnit.SECONDS
    }

    lateinit var timeElapsedAction: () -> Unit
    private lateinit var timeDisposable: Disposable
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