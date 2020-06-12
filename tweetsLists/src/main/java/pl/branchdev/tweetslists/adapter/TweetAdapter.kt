package pl.branchdev.tweetslists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.branchdev.tweetslists.R
import pl.branchdev.tweetslists.data.Tweet

class TweetAdapter : RecyclerView.Adapter<TweetViewHolder>() {
    private var tweets: MutableList<Tweet> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tweet, parent, false)
        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(tweets[position])
    }

    override fun getItemCount(): Int = tweets.count()
    fun updateTweets(list: List<Tweet>) {
        val diffCallback =
            TweetsDiffUtil(tweets, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tweets = list.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}