package pl.branchdev.tweetsrepository.di

import org.koin.dsl.module
import pl.branchdev.tweetsrepository.TwitterRepository
import pl.branchdev.tweetsrepository.api.ApiTwitterRepository

val twitterRepositoryModule = module {
    single<TwitterRepository> { ApiTwitterRepository(get(), get()) }
}
