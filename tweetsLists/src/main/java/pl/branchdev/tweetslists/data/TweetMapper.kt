package pl.branchdev.tweetslists

import pl.branchdev.data.TweetDto
import pl.branchdev.tweetslists.Tweet

fun TweetDto.mapToTweet(): Tweet =
    Tweet(this.id ?: 0L, this.text ?: "")