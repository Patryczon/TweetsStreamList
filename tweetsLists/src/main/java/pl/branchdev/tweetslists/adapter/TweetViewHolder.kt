package pl.branchdev.tweetslists

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tweet.view.*
import pl.branchdev.tweetslists.Tweet

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Tweet) {
        itemView.tweetText.text = model.text
    }
}