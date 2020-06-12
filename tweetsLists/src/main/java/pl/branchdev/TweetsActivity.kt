package pl.branchdev

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_tweets.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.branchdev.tweetslists.R
import pl.branchdev.tweetslists.TweetListViewModel
import pl.branchdev.tweetslists.adapter.MarginItemDecoration
import pl.branchdev.tweetslists.adapter.TweetAdapter
import pl.branchdev.tweetslists.data.Tweet

class TweetsActivity : AppCompatActivity() {
    private val tweetListViewModel: TweetListViewModel by viewModel()
    private val tweetsAdapter = TweetAdapter()
    private val tweetsListLayoutManager by lazy { LinearLayoutManager(this) }
    private val tweetListObserver = Observer<List<Tweet>> { tweetsAdapter.updateTweets(it) }
    private val subscriptionComposite = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)
        initTweetList()
        initSearch()
        setupViewModel()
    }

    private fun initSearch() {
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {}
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                tweetListViewModel.searchQuery = text.toString()
            }
        })
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
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.tweet_margin).toInt()
                )
            )
        }
    }
}