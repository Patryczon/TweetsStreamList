package pl.branchdev.tweetslists.adapter

import androidx.recyclerview.widget.DiffUtil
import pl.branchdev.tweetslists.data.Tweet

class TweetsDiffUtil(
    private val oldList: List<Tweet>,
    private val newList: List<Tweet>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].text == newList[newItemPosition].text
}