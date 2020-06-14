package pl.branchdev.tweetslists

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetslists.timeSpan.TimeCounter
import pl.branchdev.tweetslists.timeSpan.TimeSpanCounterBatchHandler

class TimeSpanCounterBatchHandlerTest {
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
    fun `when add time counter to a batch it should automatically start`() {
        val timeCounter: TimeCounter = mock()
        val timeCounterBatchHandler = TimeSpanCounterBatchHandler()
        timeCounterBatchHandler.addCounter(timeCounter)
        verify(timeCounter).startCount()
    }

    @Test
    fun `when stop counting called on a batch it should call stop for every counter`() {
        val timeCounter: TimeCounter = mock()
        val timeCounter2: TimeCounter = mock()
        val timeCounterBatchHandler = TimeSpanCounterBatchHandler()
        timeCounterBatchHandler.addCounter(timeCounter)
        timeCounterBatchHandler.addCounter(timeCounter2)
        timeCounterBatchHandler.stopCounting()
        verify(timeCounter).stopCount()
        verify(timeCounter2).stopCount()
    }
}