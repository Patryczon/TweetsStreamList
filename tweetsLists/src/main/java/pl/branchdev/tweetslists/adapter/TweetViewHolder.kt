package pl.branchdev.tweetslists.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tweet.view.*
import pl.branchdev.tweetslists.R
import pl.branchdev.tweetslists.data.Tweet

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Tweet) {
        itemView.apply {
            tweetText.text = model.text
            tweetAuthor.text = resources.getString(R.string.tweet_author, model.author)
        }
    }
}