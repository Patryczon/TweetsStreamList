package pl.branchdev.tweetsstreamlist.di

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.branchdev.tweetsrepository.api.ApiConfigurationData
import pl.branchdev.tweetsrepository.di.networkModule
import pl.branchdev.tweetsrepository.di.twitterRepositoryModule
import pl.branchdev.tweetsstreamlist.BuildConfig
import pl.branchdev.tweetsstreamlist.tweetsList.TweetListViewModel

val viewModelsModule = module {
    viewModel {
        TweetListViewModel(
            get(),
            ReactiveNetwork.observeNetworkConnectivity(androidContext())
                .subscribeOn(Schedulers.io())
                .map { return@map it.available() }
                .subscribeOn(Schedulers.io())
        )
    }
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
    ), twitterRepositoryModule, viewModelsModule
)
