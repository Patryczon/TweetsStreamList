package pl.branchdev.tweetsstreamlist.tweetsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tweet.view.*

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Tweet) {
        itemView.tweetText.text = model.text
    }
}