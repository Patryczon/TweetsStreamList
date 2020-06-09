package pl.branchdev.tweetsstreamlist.tweetsList

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TimeSpanCounter(
    timeAmount: Long = 5L,
    timeUnit: TimeUnit = TimeUnit.SECONDS
) {
    lateinit var timeElapsedAction: () -> Unit
    lateinit var timeDisposable: Disposable
    private var timeCounterDisposable: Single<*> =
        Single.timer(timeAmount, timeUnit, Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())


    fun startTimeCount() {
        timeDisposable =
            timeCounterDisposable.subscribe({ timeElapsedAction() }, { timeDisposable.dispose() })
    }

    fun stopTimeCount() {
        timeDisposable.dispose()
    }
}