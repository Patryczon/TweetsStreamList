package pl.branchdev.tweetsstreamlist.di

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.branchdev.tweetsrepository.api.ApiConfigurationData
import pl.branchdev.tweetsrepository.di.networkModule
import pl.branchdev.tweetsrepository.di.twitterRepositoryModule
import pl.branchdev.tweetsstreamlist.BuildConfig
import pl.branchdev.common.rx.AppSchedulerProvider
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetsstreamlist.timeSpan.TimeSpanBatchHandler
import pl.branchdev.tweetsstreamlist.timeSpan.TimeSpanCounterBatchHandler
import pl.branchdev.tweetsstreamlist.tweetsList.TweetListViewModel

val viewModelsModule = module {
    viewModel {
        TweetListViewModel(
            get(),
            ReactiveNetwork.observeNetworkConnectivity(androidContext())
                .subscribeOn(get<SchedulerProvider>().io())
                .map { return@map it.available() }
                .subscribeOn(get<SchedulerProvider>().io()), get(), get()
        )
    }
}
val utilsModule = module {
    single<SchedulerProvider> { AppSchedulerProvider() }
    single<TimeSpanBatchHandler> { TimeSpanCounterBatchHandler() }
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
