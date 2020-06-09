package pl.branchdev.tweetsstreamlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.branchdev.tweetsstreamlist.connectivity.InternetConnectionReceiver
import pl.branchdev.tweetsstreamlist.connectivity.InternetConnectionReceiver.Companion.IS_NETWORK_AVAILABLE
import pl.branchdev.tweetsstreamlist.tweetsList.Tweet
import pl.branchdev.tweetsstreamlist.tweetsList.TweetAdapter
import pl.branchdev.tweetsstreamlist.tweetsList.TweetListViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork

class MainActivity : AppCompatActivity() {
    private val tweetListViewModel: TweetListViewModel by viewModel()
    private val tweetsAdapter = TweetAdapter()
    private val tweetsListLayoutManager by lazy { LinearLayoutManager(this) }
    private val tweetListObserver = Observer<List<Tweet>> { tweetsAdapter.updateTweets(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTweetList()
        setupViewModel()
        listenToConnectionReciver()
    }

    private fun listenToConnectionReciver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val isNetworkAvailable =
                        intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false)
                    tweetListViewModel.updateConnectionState(isNetworkAvailable)
                }
            }, IntentFilter(InternetConnectionReceiver.NETWORK_AVAILABLE_ACTION)
        )
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
