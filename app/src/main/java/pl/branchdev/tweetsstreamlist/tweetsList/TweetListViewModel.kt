package pl.branchdev.tweetsstreamlist.tweetsList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.branchdev.tweetsrepository.TwitterRepository

class TweetListViewModel(
    private val repository: TwitterRepository,
    private val connectionObserver: Observable<Boolean>
) : ViewModel() {
    private var internetDisposable: Disposable
    private lateinit var statusesDisposable: Disposable
    private val tweets = mutableListOf<Tweet>()
    val tweetsLiveData by lazy { MutableLiveData<List<Tweet>>() }
    private val timeSpans = mutableListOf<TimeSpanCounter>()


    init {
        internetDisposable = connectionObserver.retry().subscribe({
            updateConnectionState(it)
            Log.i("connectionState", it.toString())
        },
            { Log.e("internetConnection", it?.message) })
    }

    fun updateConnectionState(isConnected: Boolean) {
        if (isConnected) {
            reconnectToStatusStream()
        } else {
            timeSpans.forEach { timeSpan -> timeSpan.stopTimeCount() }
            timeSpans.clear()
        }
    }

    private fun reconnectToStatusStream() {
        tweets.clear()
        tweetsLiveData.postValue(tweets)
        connectToStatusesStream()
    }

    private fun connectToStatusesStream() {
        statusesDisposable = repository.statusesStreamObservable().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map {
                return@map it.mapToTweet()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                createLifeTimeSpanForTweet(it)
                tweets.add(0, it)
                tweetsLiveData.value = tweets
            }, {
                Log.e("error", it?.message)
            }, {})
    }

    private fun createLifeTimeSpanForTweet(tweet: Tweet) {
        val timeSpanCounter = TimeSpanCounter()
        timeSpanCounter.timeElapsedAction = {
            tweets.remove(tweet)
            tweetsLiveData.postValue(tweets)
            timeSpans.remove(timeSpanCounter)
        }
        timeSpans.add(timeSpanCounter)
        timeSpanCounter.startTimeCount()
    }

    override fun onCleared() {
        super.onCleared()
        statusesDisposable.dispose()
        timeSpans.forEach { it.stopTimeCount() }
        timeSpans.clear()
        internetDisposable.dispose()
    }
}