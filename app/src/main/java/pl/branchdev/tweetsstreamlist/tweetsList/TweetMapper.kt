package pl.branchdev.tweetsstreamlist.tweetsList

import TweetDto

fun TweetDto.mapToTweet(): Tweet = Tweet(this.id ?: 0L, this.text ?: "")