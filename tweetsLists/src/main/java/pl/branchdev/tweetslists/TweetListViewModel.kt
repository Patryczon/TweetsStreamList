package pl.branchdev.tweetslists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import pl.branchdev.common.logger.LoggerProvider
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetslists.data.Tweet
import pl.branchdev.tweetslists.data.mapToTweet
import pl.branchdev.tweetslists.timeSpan.TimeSpanBatchHandler
import pl.branchdev.tweetslists.timeSpan.TimeSpanCounter
import pl.branchdev.tweetsrepository.TwitterRepository
import kotlin.properties.Delegates

class TweetListViewModel(
    private val repository: TwitterRepository,
    connectionObserver: Observable<Boolean>,
    private val schedulerProvider: SchedulerProvider,
    private val timeSpanBatchHandler: TimeSpanBatchHandler,
    val logger: LoggerProvider
) : ViewModel() {
    private var internetConnectionDisposable: Disposable
    private var statusesDisposable: Disposable? = null
    private val tweets = mutableListOf<Tweet>()
    val tweetsLiveData by lazy { MutableLiveData<List<Tweet>>() }
    var searchQuery: String by Delegates.observable("") { property, oldValue, newValue ->
        reconnectToStatusStream()
    }

    init {
        internetConnectionDisposable = connectionObserver.retry().subscribe({
            updateConnectionState(it)
            logger.logInfo("connectionState", it.toString())
        },
            {
                logger.logError("internetConnection", it?.message)
            })
    }

    private fun updateConnectionState(isConnected: Boolean) {
        if (isConnected) {
            reconnectToStatusStream()
        } else {
            timeSpanBatchHandler.stopCounting()
        }
    }

    private fun reconnectToStatusStream() {
        clearTweetsList()
        connectToStatusesStream()
    }

    private fun clearTweetsList() {
        tweets.clear()
        tweetsLiveData.postValue(tweets)
    }

    private fun connectToStatusesStream() {
        statusesDisposable?.dispose()
        statusesDisposable =
            repository.statusesStreamObservable(searchQuery)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.computation())
                .map { return@map it.mapToTweet() }
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    createLifeTimeSpanForTweet(it)
                    tweets.add(0, it)
                    tweetsLiveData.value = tweets
                }, {
                    logger.logError("error", it?.message)
                }, {})
    }

    private fun createLifeTimeSpanForTweet(tweet: Tweet) {
        val timeSpanCounter =
            TimeSpanCounter(
                schedulerProvider = schedulerProvider
            )
        timeSpanCounter.timeElapsedAction = {
            tweets.remove(tweet)
            tweetsLiveData.postValue(tweets)
            timeSpanBatchHandler.removeCounter(timeSpanCounter)
        }
        timeSpanBatchHandler.addCounter(timeSpanCounter)
    }

    override fun onCleared() {
        super.onCleared()
        statusesDisposable?.dispose()
        timeSpanBatchHandler.stopCounting()
        internetConnectionDisposable.dispose()
    }
}