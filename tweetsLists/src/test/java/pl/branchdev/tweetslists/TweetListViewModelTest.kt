package pl.branchdev.tweetslists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.internal.junit.ExceptionFactory
import pl.branchdev.common.logger.LoggerProvider
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.data.TweetDto
import pl.branchdev.tweetslists.data.Tweet
import pl.branchdev.tweetslists.timeSpan.TimeSpanBatchHandler
import pl.branchdev.tweetsrepository.TwitterRepository

class TweetListViewModelTest {
    private val mockedRepository: TwitterRepository = mock()
    private val testConnectionSubject = PublishSubject.create<Boolean>()
    private val mockedSchedulerProvider: SchedulerProvider = mock()
    private val mockedTimeSpanBatchHandler: TimeSpanBatchHandler = mock()
    private val mockedLogger: LoggerProvider = mock()
    private val tweetTestSubject = PublishSubject.create<TweetDto>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val viewModel =
        TweetListViewModel(
            repository = mockedRepository,
            connectionObserver = testConnectionSubject,
            timeSpanBatchHandler = mockedTimeSpanBatchHandler,
            schedulerProvider = mockedSchedulerProvider,
            logger = mockedLogger
        )

    @Before
    fun setup() {
        Mockito.reset(mockedRepository, mockedSchedulerProvider)
        whenever(mockedSchedulerProvider.io()).thenReturn(Schedulers.trampoline())
        whenever(mockedSchedulerProvider.computation()).thenReturn(Schedulers.trampoline())
        whenever(mockedSchedulerProvider.ui()).thenReturn(Schedulers.trampoline())
        whenever(mockedRepository.statusesStreamObservable(any())).thenReturn(tweetTestSubject)
    }

    @Test
    fun `when viewmodel initialize then connectionObservable should have observer`() {
        assertTrue(testConnectionSubject.hasObservers())
    }

    @Test
    fun `when connection subject emit true then view model should connect to stream`() {
        testConnectionSubject.onNext(true)
        verify(mockedRepository).statusesStreamObservable(any())
    }

    @Test
    fun `when connection subject emit true then tweets data should be cleared`() {
        val tweet = Tweet(2, "","")
        viewModel.tweetsLiveData.value = mutableListOf(tweet)
        testConnectionSubject.onNext(true)
        assertTrue(viewModel.tweetsLiveData.value.isNullOrEmpty())
    }

    @Test
    fun `when connection subject emit false then should stop time span counter`() {
        testConnectionSubject.onNext(false)
        verify(mockedTimeSpanBatchHandler).stopCounting()
    }

    @Test
    fun `when viewmodel is connected to stream and it emits then should add mapped tweet object with same data to livedata`() {
        testConnectionSubject.onNext(true)
        assertTrue(viewModel.tweetsLiveData.value.isNullOrEmpty())
        val id = 1L
        tweetTestSubject.onNext(
            TweetDto(
                null,
                id,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
        assertTrue(viewModel.tweetsLiveData.value?.any { it.id == id } ?: false)
    }

    @Test
    fun `when viewmodel is connected to stream and it emits then should add time counter to batch`() {
        testConnectionSubject.onNext(true)
        val id = 1L
        tweetTestSubject.onNext(
            TweetDto(
                null,
                id,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
        verify(mockedTimeSpanBatchHandler).addCounter(any())
    }

    @Test
    fun `when viewmodel is connected to stream and it emits error then should log that error`() {
        testConnectionSubject.onNext(true)
        val excpetionMessage = "Exception thrown!"
        tweetTestSubject.onError(
            ExceptionFactory.createArgumentsAreDifferentException(
                excpetionMessage,
                "",
                ""
            )
        )
        verify(mockedLogger).logError(any(), eq(excpetionMessage))
    }

    @Test
    fun `when connectionObservable emit false then should stop batch timer`() {
        testConnectionSubject.onNext(false)
        verify(mockedTimeSpanBatchHandler).stopCounting()
    }

    @Test
    fun `when statuses observable emit exception then it should be logged in logger`() {
        val excpetionMessage = "Exception thrown!"
        testConnectionSubject.onNext(true)
        tweetTestSubject.onError(
            ExceptionFactory.createArgumentsAreDifferentException(
                excpetionMessage,
                "",
                ""
            )
        )
        verify(mockedLogger).logError(any(), eq(excpetionMessage))
    }

    @Test
    fun `when search query value it's change then should call connect to stream with itself value`() {
        val searchQuery = "abc"
        viewModel.searchQuery = searchQuery
        verify(mockedRepository).statusesStreamObservable(eq(searchQuery))
    }
}