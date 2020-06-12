package pl.branchdev.tweetslists

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetsstreamlist.timeSpan.TimeSpanCounter
import java.util.concurrent.TimeUnit


class TimeSpanCounterTest {
    private val testSchedulerComputation = TestScheduler()
    private val testSchedulerMain = Schedulers.trampoline()
    private val mockedSchedulerProvider: SchedulerProvider = mock()

    init {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        whenever(mockedSchedulerProvider.computation()).thenReturn(testSchedulerComputation)
        whenever(mockedSchedulerProvider.ui()).thenReturn(testSchedulerMain)
    }

    @Test
    fun `when start time count and time elapsed then should call time elapsed action`() {
        val mockedAction: () -> Unit = mock()
        val timeSpanCounter =
            TimeSpanCounter(
                timeAmount = 1,
                timeUnit = TimeUnit.SECONDS,
                schedulerProvider = mockedSchedulerProvider
            )
        timeSpanCounter.timeElapsedAction = mockedAction
        timeSpanCounter.startCount()
        testSchedulerComputation.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(mockedAction).invoke()
    }

    @Test
    fun `when start time count and time not elapsed then elapsed action shouldn't be called`() {
        val mockedAction: () -> Unit = mock()
        val timeSpanCounter =
            TimeSpanCounter(
                timeAmount = 1,
                timeUnit = TimeUnit.SECONDS,
                schedulerProvider = mockedSchedulerProvider
            )
        timeSpanCounter.timeElapsedAction = mockedAction
        timeSpanCounter.startCount()
        verifyZeroInteractions(mockedAction)
    }

    @Test
    fun `when start time count and dispose before time elapsed elapsed then elapsed action shouldn't be called`() {
        val mockedAction: () -> Unit = mock()
        val timeSpanCounter =
            TimeSpanCounter(
                timeAmount = 1,
                timeUnit = TimeUnit.SECONDS,
                schedulerProvider = mockedSchedulerProvider
            )
        timeSpanCounter.timeElapsedAction = mockedAction
        timeSpanCounter.startCount()
        testSchedulerComputation.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        timeSpanCounter.stopCount()
        testSchedulerComputation.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        verifyZeroInteractions(mockedAction)
    }
}