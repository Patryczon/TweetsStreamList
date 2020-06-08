package pl.branchdev.tweetsstreamlist.tweetsList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Tweet) {
        tweetText.text = model.text
    }
}