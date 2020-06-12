package pl.branchdev.tweetsstreamlist.di

import org.koin.dsl.module
import pl.branchdev.common.logger.ConsoleLogger
import pl.branchdev.common.logger.LoggerProvider
import pl.branchdev.common.rx.AppSchedulerProvider
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetslists.di.viewModelsModule
import pl.branchdev.tweetslists.timeSpan.TimeSpanBatchHandler
import pl.branchdev.tweetslists.timeSpan.TimeSpanCounterBatchHandler
import pl.branchdev.tweetsrepository.api.ApiConfigurationData
import pl.branchdev.tweetsrepository.di.networkModule
import pl.branchdev.tweetsrepository.di.twitterRepositoryModule
import pl.branchdev.tweetsstreamlist.BuildConfig

val utilsModule = module {
    single<SchedulerProvider> { AppSchedulerProvider() }
    single<TimeSpanBatchHandler> { TimeSpanCounterBatchHandler() }
    single<LoggerProvider> { ConsoleLogger() }
}
val modules = listOf(
    networkModule(
        ApiConfigurationData(
            baseUrl = BuildConfig.baseUrl,
            consumerKey = BuildConfig.twitterConsumerKey,
            consumerKeySecret = BuildConfig.twitterConsumerSecretKey,
            token = BuildConfig.twitterToken,
            tokenSecret = BuildConfig.twitterTokenSecret
        )
    ), twitterRepositoryModule, viewModelsModule, utilsModule
)