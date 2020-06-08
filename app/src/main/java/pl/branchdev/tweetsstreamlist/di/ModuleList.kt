package pl.branchdev.tweetsstreamlist.di

import pl.branchdev.tweetsrepository.api.ApiConfigurationData
import pl.branchdev.tweetsrepository.di.networkModule
import pl.branchdev.tweetsrepository.di.twitterRepositoryModule
import pl.branchdev.tweetsstreamlist.BuildConfig

val modules = listOf(
    networkModule(
        ApiConfigurationData(
            baseUrl = BuildConfig.baseUrl,
            consumerKey = BuildConfig.twitterConsumerKey,
            consumerKeySecret = BuildConfig.twitterConsumerSecretKey,
            token = BuildConfig.twitterToken,
            tokenSecret = BuildConfig.twitterTokenSecret
        )
    ), twitterRepositoryModule
)