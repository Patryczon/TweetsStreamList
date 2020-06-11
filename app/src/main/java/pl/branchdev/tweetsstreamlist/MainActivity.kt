package pl.branchdev.tweetsstreamlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.branchdev.common.logger.LoggerProvider
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.common.rx.addTo
import pl.branchdev.tweetsstreamlist.tweetsList.Tweet
import pl.branchdev.tweetsstreamlist.tweetsList.TweetAdapter
import pl.branchdev.tweetsstreamlist.tweetsList.TweetListViewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val tweetListViewModel: TweetListViewModel by viewModel()
    private val tweetsAdapter = TweetAdapter()
    private val tweetsListLayoutManager by lazy { LinearLayoutManager(this) }
    private val tweetListObserver = Observer<List<Tweet>> { tweetsAdapter.updateTweets(it) }
    private val subscriptionComposite = CompositeDisposable()
    private val schedulerProvider: SchedulerProvider by inject()
    private val logger: LoggerProvider by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTweetList()
        initSearch()
        setupViewModel()
    }

    private fun initSearch() {
        RxTextView.textChanges(searchInput).debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(schedulerProvider.ui())
            .observeOn(schedulerProvider.io())
            .subscribe({
                tweetListViewModel.searchQuery = it.toString()
            }, {
                logger.logError("searchError", it?.message)
            }).addTo(subscriptionComposite)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptionComposite.dispose()
        subscriptionComposite.clear()
    }


    private fun setupViewModel() {
        tweetListViewModel.tweetsLiveData.observe(this, tweetListObserver)
    }

    private fun initTweetList() {
        tweetsList.apply {
            layoutManager = tweetsListLayoutManager
            adapter = tweetsAdapter
        }
    }
}
