package pl.branchdev.tweetsstreamlist.tweetsList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import pl.branchdev.tweetsrepository.TwitterRepository
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetsstreamlist.timeSpan.TimeSpanBatchHandler
import pl.branchdev.tweetsstreamlist.timeSpan.TimeSpanCounter

class TweetListViewModel(
    private val repository: TwitterRepository,
    connectionObserver: Observable<Boolean>,
    private val schedulerProvider: SchedulerProvider,
    private val timeSpanBatchHandler: TimeSpanBatchHandler
) : ViewModel() {
    private var internetDisposable: Disposable
    private lateinit var statusesDisposable: Disposable
    private val tweets = mutableListOf<Tweet>()
    val tweetsLiveData by lazy { MutableLiveData<List<Tweet>>() }


    init {
        internetDisposable = connectionObserver.retry().subscribe({
            updateConnectionState(it)
            Log.i("connectionState", it.toString())
        },
            { Log.e("internetConnection", it?.message) })
    }

    private fun updateConnectionState(isConnected: Boolean) {
        if (isConnected) {
            reconnectToStatusStream()
        } else {
            timeSpanBatchHandler.stopCounting()
        }
    }

    private fun reconnectToStatusStream() {
        tweets.clear()
        tweetsLiveData.postValue(tweets)
        connectToStatusesStream()
    }

    private fun connectToStatusesStream() {
        statusesDisposable =
            repository.statusesStreamObservable().subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.computation())
                .map {
                    return@map it.mapToTweet()
                }
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    createLifeTimeSpanForTweet(it)
                    tweets.add(0, it)
                    tweetsLiveData.value = tweets
                }, {
                    Log.e("error", it?.message)
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
        statusesDisposable.dispose()
        timeSpanBatchHandler.stopCounting()
        internetDisposable.dispose()
    }
}