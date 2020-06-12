package pl.branchdev.tweetslists.data

import pl.branchdev.data.TweetDto

fun TweetDto.mapToTweet(): Tweet =
    Tweet(this.id ?: 0L, this.text ?: "", this?.user?.name ?: "")