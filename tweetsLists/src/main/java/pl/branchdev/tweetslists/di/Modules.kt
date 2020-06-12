package pl.branchdev.tweetslists.di

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetslists.TweetListViewModel

val viewModelsModule = org.koin.dsl.module {
    viewModel {
        TweetListViewModel(
            get(),
            ReactiveNetwork.observeNetworkConnectivity(androidContext())
                .subscribeOn(get<SchedulerProvider>().io())
                .map { return@map it.available() }
                .subscribeOn(get<pl.branchdev.common.rx.SchedulerProvider>().io()), get(), get(),
            get()
        )
    }
}